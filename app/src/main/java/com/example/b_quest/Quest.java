package com.example.b_quest;

import java.io.Serializable;

//CODE BY JUAN MARTIN

//Quest class defines the attributes for each quest that will be used in the app
public class Quest implements Serializable {

    private String questCategory;
    private String questName;
    private String questDescription;
    private String status;

    public Quest() {
    }

    public Quest(String questCategory, String questName, String questDescription, String status) {
        this.questCategory = questCategory;
        this.questName = questName;
        this.questDescription = questDescription;
        this.status = status;
    }

    public String getQuestCategory() {
        return questCategory;
    }

    public void setQuestCategory(String questCategory) {
        this.questCategory = questCategory;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public void setQuestDescription(String questDescription) {
        this.questDescription = questDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
