package com.dgd.domain.dto;


import lombok.Getter;

@Getter
public class Map {
    private double latitude;
    private double longitude;

    public Map(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
