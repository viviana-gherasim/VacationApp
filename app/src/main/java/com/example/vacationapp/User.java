package com.example.vacationapp;

public class User {
    public String email, password, day, month, year;

    public User() {

    }

    public User(String email, String password, String day, String month, String year) {
        this.email=email;
        this.password=password;
        this.day=day;
        this.month=month;
        this.year=year;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getDay() { return day; }

    public void setDay(String day) { this.day = day; }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }
}
