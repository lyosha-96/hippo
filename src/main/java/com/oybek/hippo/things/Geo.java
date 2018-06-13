package com.oybek.hippo.things;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geo {
    private double latitude;
    private double longitude;

    @JsonProperty("coordinates")
    public void setCoordinates(String coordinates) {
        String[] splitted = coordinates.split("\\s+");
        this.latitude = Double.parseDouble(splitted[0]);
        this.longitude = Double.parseDouble(splitted[1]);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

