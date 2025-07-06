package com.example.trackly;

public class UserProfile {
    private String fullName;
    private String birthDate;
    private String email;

    // Empty constructor required for Firestore deserialization
    public UserProfile() {
    }

    public UserProfile(String fullName, String birthDate, String email) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
    }

    // Getters
    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
