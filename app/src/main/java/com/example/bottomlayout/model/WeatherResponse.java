package com.example.bottomlayout.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("location")
    private LocationInfo location;

    @SerializedName("current")
    private CurrentWeatherInfo currentWeatherInfo;

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public CurrentWeatherInfo getCurrentWeatherInfo() {
        return currentWeatherInfo;
    }

    public void setCurrentWeatherInfo(CurrentWeatherInfo currentWeatherInfo) {
        this.currentWeatherInfo = currentWeatherInfo;
    }

    // Model class for the "location" object
    public static class LocationInfo {
        @SerializedName("name")
        private String cityName;

        @SerializedName("region")
        private String region;

        @SerializedName("country")
        private String country;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    // Model class for the "current" object
    public static class CurrentWeatherInfo {
        @SerializedName("temp_c")
        private String temperatureCelsius;

        @SerializedName("condition")
        private ConditionInfo conditionInfo;

        public String getTemperatureCelsius() {
            return temperatureCelsius;
        }

        public void setTemperatureCelsius(String temperatureCelsius) {
            this.temperatureCelsius = temperatureCelsius;
        }

        public ConditionInfo getConditionInfo() {
            return conditionInfo;
        }

        public void setConditionInfo(ConditionInfo conditionInfo) {
            this.conditionInfo = conditionInfo;
        }
    }

    // Model class for the "condition" object
    public static class ConditionInfo {
        @SerializedName("text")
        private String conditionText;

        public String getConditionText() {
            return conditionText;
        }

        public void setConditionText(String conditionText) {
            this.conditionText = conditionText;
        }
    }

    // New model class for a list of conditions
    public static class ConditionsList {
        @SerializedName("conditions")
        private List<ConditionInfo> conditions;

        public List<ConditionInfo> getConditions() {
            return conditions;
        }
    }
}
