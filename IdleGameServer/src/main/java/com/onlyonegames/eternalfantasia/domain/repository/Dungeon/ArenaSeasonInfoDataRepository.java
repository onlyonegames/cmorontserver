package com.onlyonegames.eternalfantasia.domain.repository.Dungeon;

import com.onlyonegames.eternalfantasia.domain.model.entity.Dungeon.ArenaSeasonInfoData;
import com.onlyonegames.eternalfantasia.domain.model.entity.MyCharacters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ArenaSeasonInfoDataRepository extends JpaRepository<ArenaSeasonInfoData, Integer> {
    //@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    Optional<ArenaSeasonInfoData> findByNowSeasonNo(int seasonNo);
}
