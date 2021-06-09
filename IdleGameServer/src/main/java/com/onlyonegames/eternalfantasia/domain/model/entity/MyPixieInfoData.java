package com.onlyonegames.eternalfantasia.domain.model.entity;

import com.onlyonegames.eternalfantasia.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class MyPixieInfoData extends BaseTimeEntity {
    @Id
    @TableGenerator(name = "hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate_sequence")
    Long id;
    Long useridUser;
    int level;
    Long exp;
    Long maxExp;
    Long runeSlot1;
    Long runeSlot2;
    Long runeSlot3;
    Long runeSlot4;
    Long runeSlot5;
    Long runeSlot6;

    public void GetExp(Long addExp) { //TODO 공식 변경 가능성 있음 중요!!
        Long temp = addExp;
        do{
            if(this.exp + temp >= this.maxExp){
                temp -= this.maxExp - this.exp;
                this.exp = 0L;
                this.level += 1;
                this.maxExp += this.level * (3 * this.level + 1);
            } else {
                this.exp += temp;
                temp -= temp;
            }
        }while (temp != 0L);

    }

    public Long EquipmentRune(Long runeInventoryId, int slotNo){
        Long temp = 0L;
        switch(slotNo){
            case 1:
                if(!runeSlot1.equals(0L))
                    temp = runeSlot1;
                runeSlot1 = runeInventoryId;
                break;
            case 2:
                if(!runeSlot2.equals(0L))
                    temp = runeSlot2;
                runeSlot2 = runeInventoryId;
                break;
            case 3:
                if(!runeSlot3.equals(0L))
                    temp = runeSlot3;
                runeSlot3 = runeInventoryId;
                break;
            case 4:
                if(!runeSlot4.equals(0L))
                    temp = runeSlot4;
                runeSlot4 = runeInventoryId;
                break;
            case 5:
                if(!runeSlot5.equals(0L))
                    temp = runeSlot5;
                runeSlot5 = runeInventoryId;
                break;
            case 6:
                if(!runeSlot6.equals(0L))
                    temp = runeSlot6;
                runeSlot6 = runeInventoryId;
                break;

        }
        return temp;
    }

    public Long UnEquipmentRune(int slotNo) {
        Long temp = 0L;
        switch(slotNo){
            case 1:
                if(!runeSlot1.equals(0L))
                    temp = runeSlot1;
                runeSlot1 = 0L;
                break;
            case 2:
                if(!runeSlot2.equals(0L))
                    temp = runeSlot2;
                runeSlot2 = 0L;
                break;
            case 3:
                if(!runeSlot3.equals(0L))
                    temp = runeSlot3;
                runeSlot3 = 0L;
                break;
            case 4:
                if(!runeSlot4.equals(0L))
                    temp = runeSlot4;
                runeSlot4 = 0L;
                break;
            case 5:
                if(!runeSlot5.equals(0L))
                    temp = runeSlot5;
                runeSlot5 = 0L;
                break;
            case 6:
                if(!runeSlot6.equals(0L))
                    temp = runeSlot6;
                runeSlot6 = 0L;
                break;

        }
        return temp;
    }
}
