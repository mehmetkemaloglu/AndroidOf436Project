package com.visionless.earthquake;

public class District {
    public String name;
    public int earthquakeNumber;


    public District(String name, int earthquakeNumber) {
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
}
