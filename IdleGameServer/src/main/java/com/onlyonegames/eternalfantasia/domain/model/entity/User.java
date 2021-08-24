package com.onlyonegames.eternalfantasia.domain.model.entity;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.onlyonegames.eternalfantasia.etc.DefineLimitValue;
import com.onlyonegames.eternalfantasia.etc.Defines;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @TableGenerator(name = "hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate_sequence")
    Long id;
    String socialId;
    String password;
    String socialProvider;
    String userGameName;
    int level;
    int exp;
    int sexType; //0 : 남자, 1 : 여자 default : 0
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    String gold;
    Long diamond;
    String soulStone; // 형식 변경 가능성 있음
    int skillPoint;
    int moveStone;
    Long arenaCoin;
    Long dragonCoin;
    int fieldIndex;
    Long dungeonTicket;
    String battleStatus;
    boolean dummyUser;
    boolean new_user;
    int userType;
    Long battlePower;


    @CreatedDate
    private LocalDateTime createdDate;
    LocalDateTime lastloginDate;
    LocalDateTime lastSettingTime;

    @Builder
    public User(String socialId, String password, String socialProvider, String userGameName, Set<Role> roles) {
        this.socialId = socialId;
        this.password = password;
        this.socialProvider = socialProvider;
        this.userGameName = userGameName;
        this.roles = roles;
        InitData();
        SetLastLoginDate();
    }

    public void InitData() {
        this.gold = "0";
        this.diamond = 0L;
        this.soulStone = "0";
        this.skillPoint = 0;
        this.moveStone = 100; //TODO 라이브때 0으로 설정해야함 !!!!!
        this.arenaCoin = 0L;
        this.dragonCoin = 0L;
        this.level = 1;
        this.exp = 0;
        this.sexType = 0;
        this.fieldIndex = 0;
        this.battleStatus = "";
        this.dungeonTicket = 0L;
        this.dummyUser = false;
        this.new_user = true;
        this.userType = 1;
        this.battlePower = 0L;
    }

    public void AddGold(BigInteger _addGold) {
        BigInteger gold = new BigInteger(this.gold);
        gold = gold.add(_addGold);
        this.gold = gold.toString();
    }

    public boolean SpendGold(BigInteger spendGold) {
        BigInteger gold = new BigInteger(this.gold);
        if(gold.compareTo(spendGold)>=0){
            gold = gold.subtract(spendGold);
            this.gold = gold.toString();
            return true;
        }
        return false;
    }

    public void AddSoulStone(Long _addSoulStone) {
        BigInteger soulStone = new BigInteger(this.soulStone);
        soulStone = soulStone.add(BigInteger.valueOf(_addSoulStone));
        this.soulStone = soulStone.toString();
    }

    public boolean SpendSoulStone(BigInteger spendSoulStone) {
        BigInteger soulStone = new BigInteger(this.soulStone);
        if(soulStone.compareTo(spendSoulStone)>=0){
            soulStone = soulStone.subtract(spendSoulStone);
            this.soulStone = soulStone.toString();
            return true;
        }
        return false;
    }

    public boolean SpendDiamond(int spendDiamond) {
        if(spendDiamond>this.diamond)
            return false;
        this.diamond -= spendDiamond;
        return true;
    }

    public void SetLastLoginDate() {
        this.lastloginDate = LocalDateTime.now();
    }

    public void SetUserName(String userGameName) {
        this.userGameName = userGameName;
    }

    public void SetNew_User() {
        this.new_user = false;
    }

    public void AddSkillPoint(int addSkillPoint) {
        this.skillPoint += addSkillPoint;
    }

    public boolean SpendSkillPoint(int spendSkillPoint) {
        if(this.skillPoint >= spendSkillPoint) {
            this.skillPoint -= spendSkillPoint;
            return true;
        }
        return false;
    }

    public void AddArenaCoin(Long addArenaCoin) {
        this.arenaCoin += addArenaCoin;
    }

    public void SetLastSettingTime() {
        this.lastSettingTime = LocalDateTime.now();
    }

    public void SetSexType(int sexType) {
        this.sexType = sexType;
    }

    public void SetGold(String element) {
        this.gold = element;
    }

    public void SetDiamond(String element) {
        this.diamond = Long.parseLong(element);
    }

    public void SetSoulStone(String element) {
        this.soulStone = element;
    }

    public void SetSkillPoint(String element) {
        this.skillPoint = Integer.parseInt(element);
    }

    public void SetMoveStone(String element) {
        this.moveStone = Integer.parseInt(element);
    }

    public void SetLevel(String element) {
        this.level = Integer.parseInt(element);
    }

    public void SetExp(String element) {
        this.exp = Integer.parseInt(element);
    }

    public void SetFieldIndex(String element) {
        this.fieldIndex = Integer.parseInt(element);
    }

    public void SetBattleStatus(String element) {
        this.battleStatus = element;
    }

    public void SetDungeonTicket(String element) {
        this.dungeonTicket = Long.parseLong(element);
    }

    public void SetArenaCoin(String element) {
        this.arenaCoin = Long.parseLong(element);
    }

    public void SetDragonCoin(String element) {
        this.dragonCoin = Long.parseLong(element);
    }

    public void SetBattlePower(String element) {
        this.battlePower = Long.parseLong(element);
    }
}