package com.example.personalhealthmonitoringapp;

public class AppointmentModel {

    String id, AppDocName, AppDate, AppTime, AppReason;
    
    public AppointmentModel(){}

    public AppointmentModel(String id , String AppDocName , String AppDate , String AppTime, String AppReason){
        this.id = id;
        this.AppDocName = AppDocName;
        this.AppDate = AppDate;
        this.AppTime = AppTime;
        this.AppReason = AppReason;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppDocName() {
        return AppDocName;
    }

    public void setAppDocName(String appDocName) {
        AppDocName = appDocName;
    }

    public String getAppDate() {
        return AppDate;
    }

    public void setAppDate(String appDate) {
        AppDate = appDate;
    }

    public String getAppTime() {
        return AppTime;
    }

    public void setAppTime(String appTime) {
        AppTime = appTime;
    }

    public String getAppReason() {
        return AppReason;
    }

    public void setAppReason(String appReason) {
        AppReason = appReason;
    }
}
