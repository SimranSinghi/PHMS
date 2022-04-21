package com.example.personalhealthmonitoringapp;

public class medicationmodel {
    String id,DName, Health_cond, Med_pres, Med_time,  notes,date;

    public medicationmodel(){}

    public  medicationmodel(String id,String DName,String date,String Health_cond,String Med_pres, String Med_time, String notes){
        this.id=id;
        this.DName=DName;
        this.Health_cond= Health_cond;
        this.Med_pres=Med_pres;
        this.Med_time=Med_time;
        this.notes=notes;
        this.date=date;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public String getHealth_cond() {
        return Health_cond;
    }

    public void setHealth_cond(String health_cond) {
        Health_cond = health_cond;
    }

    public String getMed_pres() {
        return Med_pres;
    }

    public void setMed_pres(String med_pres) {
        Med_pres = med_pres;
    }

    public String getMed_time() {
        return Med_time;
    }

    public void setMed_time(String med_time) {
        Med_time = med_time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
