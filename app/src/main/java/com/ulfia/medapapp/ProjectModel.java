package com.ulfia.medapapp;

public class ProjectModel {
    private String doctor_name, description, specialist, location, patients, rating;
    private String productImage;

    public ProjectModel() {
    }

    public ProjectModel(String doctor_name, String description, String specialist, String location, String patients, String rating, String productImage) {
        this.doctor_name = doctor_name;
        this.description = description;
        this.specialist = specialist;
        this.location = location;
        this.patients = patients;
        this.rating = rating;
        this.productImage = productImage;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPatients() {
        return patients;
    }

    public void setPatients(String patients) {
        this.patients = patients;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
