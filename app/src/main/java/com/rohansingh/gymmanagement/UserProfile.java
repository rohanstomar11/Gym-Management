package com.rohansingh.gymmanagement;

public class UserProfile {
    private String Name;
    private String Mobile;
    private String Date;
    private String Gender;
    private String Height;
    private  String Weight;
    private String City;
    private  String MedicalConditions;
    private  String Timings;
    private  String Packages;
    private String Core;
    private String PersonalTrainer;
    private String Zumba;
    private String Locker;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMedicalConditions() {
        return MedicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        MedicalConditions = medicalConditions;
    }

    public String getTimings() {
        return Timings;
    }

    public void setTimings(String timings) {
        Timings = timings;
    }

    public String getPackages() {
        return Packages;
    }

    public void setPackages(String packages) {
        Packages = packages;
    }

    public String getCore() {
        return Core;
    }

    public void setCore(String core) {
        Core = core;
    }

    public String getPersonalTrainer() {
        return PersonalTrainer;
    }

    public void setPersonalTrainer(String personalTrainer) {
        PersonalTrainer = personalTrainer;
    }

    public String getZumba() {
        return Zumba;
    }

    public void setZumba(String zumba) {
        Zumba = zumba;
    }

    public String getLocker() {
        return Locker;
    }

    public void setLocker(String locker) {
        Locker = locker;
    }

    public String getSteamBath() {
        return SteamBath;
    }

    public void setSteamBath(String steamBath) {
        SteamBath = steamBath;
    }

    private String SteamBath;

    public UserProfile() {
    }

    public UserProfile(String name, String mobile, String date, String gender, String height, String weight, String city, String medicalConditions, String timings, String packages, String core, String personalTrainer, String zumba, String locker, String steamBath) {
        Name = name;
        Mobile = mobile;
        Date = date;
        Gender = gender;
        Height = height;
        Weight = weight;
        City = city;
        MedicalConditions = medicalConditions;
        Timings = timings;
        Packages = packages;
        Core = core;
        PersonalTrainer = personalTrainer;
        Zumba = zumba;
        Locker = locker;
        SteamBath = steamBath;
    }
}
