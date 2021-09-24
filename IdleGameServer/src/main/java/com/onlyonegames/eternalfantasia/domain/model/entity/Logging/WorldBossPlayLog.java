package com.onlyonegames.eternalfantasia.domain.model.entity.Logging;

import com.onlyonegames.eternalfantasia.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class WorldBossPlayLog extends BaseTimeEntity {
    @Id
    @TableGenerator(name = "hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate_sequence")
    Long id;
    Long useridUser;
    Long damage;
    Long totalDamage;

    public void SetDamageAndTotalDamage(Long damage, Long totalDamage) {
        this.damage = damage;
        this.totalDamage = totalDamage;
    }
}
