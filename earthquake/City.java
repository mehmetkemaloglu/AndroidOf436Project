package com.visionless.earthquake;

public class City {
    public String name;

    public City(String name, int earthquakeNumber) {
        this.name = name;
        this.earthquakeNumber = earthquakeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEarthquakeNumber() {
        return earthquakeNumber;
    }

    public void setEarthquakeNumber(int earthquakeNumber) {
        this.earthquakeNumber = earthquakeNumber;
    }

    public int earthquakeNumber;
}
