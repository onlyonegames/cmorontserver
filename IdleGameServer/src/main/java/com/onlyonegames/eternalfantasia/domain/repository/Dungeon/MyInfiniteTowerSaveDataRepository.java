package com.onlyonegames.eternalfantasia.domain.repository.Dungeon;

import com.onlyonegames.eternalfantasia.domain.model.entity.Dungeon.MyHeroTowerExpandSaveData;
import com.onlyonegames.eternalfantasia.domain.model.entity.Dungeon.MyHeroTowerStagePlayData;
import com.onlyonegames.eternalfantasia.domain.model.entity.Dungeon.MyInfiniteTowerSaveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MyInfiniteTowerSaveDataRepository extends JpaRepository<MyInfiniteTowerSaveData, Long> {
    //@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    Optional<MyInfiniteTowerSaveData> findByUseridUser(Long userId);
}
