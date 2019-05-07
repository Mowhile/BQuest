package com.example.b_quest;

import java.util.Map;

//CODE BY JUAN MARTIN

// class defines the attributes for treasure hunts
public class TreasureHunt {

    private String treasureHuntID;
    private String treasureHunt;
    private String treasureHuntChief;
    private String heroName;
    private String heroEmail;
    private int contribution;
    private String date;
    private Map<String, Quest> questMapList;
    private Map<String, String> lordMap;
    private Map<String,String> invMap;

    public TreasureHunt() {
    }

    public TreasureHunt(String treasureHuntID, String treasureHunt,
                        String treasureHuntChief, String heroName, String heroEmail,
                        int contribution, Map<String, Quest> questMapList,String date,
                        Map<String, String> lordMap, Map<String,String> invMap) {
        this.treasureHuntID = treasureHuntID;
        this.treasureHunt = treasureHunt;
        this.treasureHuntChief = treasureHuntChief;
        this.heroName = heroName;
        this.heroEmail = heroEmail;
        this.contribution = contribution;
        this.questMapList = questMapList;
        this.lordMap = lordMap;
        this.date = date;
        this.invMap = invMap;
    }


    public String getTreasureHuntID() {
        return treasureHuntID;
    }

    public void setTreasureHuntID(String eventID) {
        this.treasureHuntID = eventID;
    }

    public String getTreasureHunt() {
        return treasureHunt;
    }

    public void setTreasureHunt(String eventName) {
        this.treasureHunt = eventName;
    }

    public String getTreasureHuntChief() {
        return treasureHuntChief;
    }

    public void setTreasureHuntChief(String treasureHuntChief) {
        this.treasureHuntChief = treasureHuntChief;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroEmail() {
        return heroEmail;
    }

    public void setHeroEmail(String heroEmail) {
        this.heroEmail = heroEmail;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public Map<String, Quest> getQuestMapList() {
        return questMapList;
    }

    public void setQuestMapList(Map<String, Quest> questMapList) {
        this.questMapList = questMapList;
    }

    public Map<String, String> getLordMap() {
        return lordMap;
    }

    public void setLordMap(Map<String, String> lordMap) {
        this.lordMap = lordMap;
    }

    public Map<String, String> getInvMap() {
        return invMap;
    }

    public void setInvMap(Map<String, String> invMap) {
        this.invMap = invMap;
    }

}

