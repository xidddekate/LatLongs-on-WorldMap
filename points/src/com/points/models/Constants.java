package com.points.models;

public class Constants {
    private final int earthRadiusKm;
    private final double radianCoeff;
    private final double degreesCoeff;
    private final String APIKEY;

    public int getEarthRadiusKm() {
        return earthRadiusKm;
    }

    public double getRadianCoeff() {
        return radianCoeff;
    }

    public double getDegreesCoeff() {
        return degreesCoeff;
    }

    public String getAPIKEY() {
        return APIKEY;
    }
    public Constants() {
        this.earthRadiusKm = 6371;
        this.radianCoeff = Math.PI / 180;
        this.degreesCoeff = 180 / Math.PI;
        this.APIKEY = "AIzaSyAEQvKUVouPDENLkQlCF6AAap1Ze-6zMos";
    }
}
