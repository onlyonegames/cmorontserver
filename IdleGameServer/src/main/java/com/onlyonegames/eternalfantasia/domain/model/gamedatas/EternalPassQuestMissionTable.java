package com.onlyonegames.eternalfantasia.domain.model.gamedatas;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "eternalpassquestmissiontable")
public class EternalPassQuestMissionTable {
    @Id
    int id;
    String code;
    String missionName;
    String missionTypeName;
    String missionParamName;
    int goalCount;
    int gettingPoint;
    String moveScene;
    String afterAction;
}
