package com.example.personalhealthmonitoringapp;

public class Model {

    String id, DrName,BP,HRate,ResRate,chol,BTemp,notes,date;
    public Model(){
    }
    public Model(String id, String DrName,String date, String BP,String HRate,String ResRate,String chol,String BTemp, String notes){
        this.id = id;
        this.DrName=DrName;
        this.BP=BP;
        this.HRate=HRate;
        this.ResRate=ResRate;
        this.chol=chol;
        this.BTemp=BTemp;
        this.notes=notes;
        this.date= date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDName() {
        return DrName;
    }

    public void setDName(String DrName) {
        this.DrName = DrName;
    }

    public String getBP() {
        return BP;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public String getHRate() {
        return HRate;
    }

    public void setHRate(String HRate) {
        this.HRate = HRate;
    }

    public String getResRate() {
        return ResRate;
    }

    public void setResRate(String resRate) {
        ResRate = resRate;
    }

    public String getChol() {
        return chol;
    }

    public void setChol(String chol) {
        this.chol = chol;
    }

    public String getBTemp() {
        return BTemp;
    }

    public void setBTemp(String BTemp) {
        this.BTemp = BTemp;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}

}
