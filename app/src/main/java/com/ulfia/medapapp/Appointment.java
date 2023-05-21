package com.ulfia.medapapp;

public class Appointment {
    private String date;
    private String time;
    private String doctor;
    private String specialist;

    // Kosong constructor diperlukan oleh Firestore
    public Appointment() {}

    public Appointment(String date, String time, String doctor, String specialist) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.specialist = specialist;
    }

    // Getter dan setter
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }
}