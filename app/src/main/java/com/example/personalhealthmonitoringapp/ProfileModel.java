package com.example.personalhealthmonitoringapp;

public class ProfileModel {

    String id , Name , Gender , Age , Height , Weight , DoctorName , DoctorVisitDate;
    public ProfileModel(){}

    public ProfileModel(String id , String Name , String Gender , String Age , String Height , String Weight , String DoctorName , String DoctorVisitDate){
        this.id = id;
        this.Name = Name;
        this.Gender = Gender;
        this.Age = Age;
        this.Height = Height;
        this.Weight = Weight;
        this.DoctorName = DoctorName;
        this.DoctorVisitDate = DoctorVisitDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getDoctorVisitDate() {
        return DoctorVisitDate;
    }

    public void setDoctorVisitDate(String doctorVisitDate) {
        DoctorVisitDate = doctorVisitDate;
    }

}
