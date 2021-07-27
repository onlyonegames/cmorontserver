package com.onlyonegames.eternalfantasia.domain.service.Contents.Leaderboard;

import com.onlyonegames.eternalfantasia.domain.MyCustomException;
import com.onlyonegames.eternalfantasia.domain.ResponseErrorCode;
import com.onlyonegames.eternalfantasia.domain.model.dto.ResponseDto.ArenaRankingInfoDto;
import com.onlyonegames.eternalfantasia.domain.model.entity.Contents.Leaderboard.ArenaRanking;
import com.onlyonegames.eternalfantasia.domain.model.entity.Contents.Leaderboard.ArenaRedisRanking;
import com.onlyonegames.eternalfantasia.domain.model.entity.User;
import com.onlyonegames.eternalfantasia.domain.repository.Contents.ArenaRankingRepository;
import com.onlyonegames.eternalfantasia.domain.repository.Contents.ArenaRedisRankingRepository;
import com.onlyonegames.eternalfantasia.domain.repository.UserRepository;
import com.onlyonegames.eternalfantasia.domain.service.ErrorLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.onlyonegames.eternalfantasia.EternalfantasiaApplication.IS_DIRECT_WRIGHDB;

@Service
@Transactional
@RequiredArgsConstructor
public class ArenaLeaderboardService {
    public static String ARENA_RANKING_LEADERBOARD = "arena_ranking_leaderboard";
    private final RedisTemplate<String, Long> redisLongTemplate;
    private final ArenaRedisRankingRepository arenaRedisRankingRepository;
    private final UserRepository userRepository;
    private final ErrorLoggingService errorLoggingService;
    private final ArenaRankingRepository arenaRankingRepository;

    public ArenaRanking setScore(Long userId, int point) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            errorLoggingService.SetErrorLog(userId, ResponseErrorCode.NOT_FIND_DATA.getIntegerValue(), "Fail! -> Cause: userId Can't find. userId => " + userId, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), IS_DIRECT_WRIGHDB);
            throw new MyCustomException("Fail! -> Cause: userId Can't find. userId => " + userId, ResponseErrorCode.NOT_FIND_DATA);
        }

        ArenaRanking arenaRanking = arenaRankingRepository.findByUseridUser(userId).orElse(null);
        if(arenaRanking == null) {
            arenaRanking = ArenaRanking.builder().useridUser(userId).userGameName(user.getUserGameName()).point(point).ranking(0).build();
            arenaRanking = arenaRankingRepository.save(arenaRanking);
        }
        else {
            arenaRanking.refresh(point);
        }
        if(!user.isDummyUser()) {
            ArenaRedisRanking arenaRedisRanking = arenaRedisRankingRepository.findById(userId).orElse(null);
            if(arenaRedisRanking == null) {
                arenaRedisRanking = ArenaRedisRanking.builder().id(userId).point(point).userGameName(user.getUserGameName()).build();
            } else {
                arenaRedisRanking.refresh(point);
            }
            arenaRedisRankingRepository.save(arenaRedisRanking);

            redisLongTemplate.opsForZSet().add(ARENA_RANKING_LEADERBOARD, userId, point);
        }
        return arenaRanking;
    }

    public Long getRank(Long userId) {
//        Long userId = arenaRanking.getUseridUser();
        Long myRank = redisLongTemplate.opsForZSet().reverseRank(ARENA_RANKING_LEADERBOARD, userId);
        if(myRank == null)
            myRank = 0L;
        myRank = myRank + 1L;
        return myRank;
    }

    public Set<Long> getRangeOfMatch(Long userId) {
        Long myRank = redisLongTemplate.opsForZSet().reverseRank(ARENA_RANKING_LEADERBOARD, userId);
        if(myRank == null)
            myRank = redisLongTemplate.opsForZSet().size(ARENA_RANKING_LEADERBOARD)-30L;
        return redisLongTemplate.opsForZSet().reverseRange(ARENA_RANKING_LEADERBOARD, myRank -30L, myRank +30L);
    }

    public Map<String, Object> GetLeaderboardForAllUser(Long userId, long bottom, long top, Map<String, Object> map) {
        Set<ZSetOperations.TypedTuple<Long>> rankings = Optional.ofNullable(redisLongTemplate.opsForZSet().reverseRangeWithScores(ARENA_RANKING_LEADERBOARD, bottom, top)).orElse(Collections.emptySet());
        List<ArenaRankingInfoDto> list = new ArrayList<>();
        int ranking = 1;
        int tempPoint = 0;
        int tempRanking = 0;
        for(ZSetOperations.TypedTuple<Long> user : rankings) {
            if(ranking > 100)
                break;
            Long id = user.getValue();
            ArenaRedisRanking value = arenaRedisRankingRepository.findById(id).get();
            if(tempPoint != value.getPoint()) {
                tempPoint = value.getPoint();
                tempRanking = ranking;
            }

            ArenaRankingInfoDto arenaRankingInfoDto = new ArenaRankingInfoDto();
            arenaRankingInfoDto.SetArenaRankingInfoDto(id, tempRanking, tempPoint);
            list.add(arenaRankingInfoDto);
            ranking++;
        }
        return map;
    }
}