package com.example.vacationapp;

import android.widget.ImageView;
import android.widget.RadioButton;

public class Destination {
    private String countryName, cityName, description, act1, act2, act3, weather;

    public Destination() {

    }

    public Destination(String countryName, String cityName, String description, String act1, String act2, String act3) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.description = description;
        this.act1 = act1;
        this.act2 = act2;
        this.act3 = act3;
    }

    public String getCountryName() { return countryName; }

    public void setCountryName(String countryName) { this.countryName = countryName; }

    public String getCityName() { return cityName; }

    public void setCityName(String cityName) {this.cityName = cityName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description=description; }

    public String getAct1Name() { return act1; }

    public void setAct1Name(String act1) { this.act1 = act1; }

    public String getAct2Name() { return act2; }

    public void setAct2Name(String act2) { this.act2 = act2; }

    public String getAct3Name() { return act3; }

    public void setAct3Name(String act3) { this.act3 = act3; }

    public String getWeather() { return weather; }

    public void setWeather(String weather) { this.weather = weather; }

}
