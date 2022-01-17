package com.points.service;

import com.points.models.Constants;
import com.points.models.Location;

public class DistanceService {
    private final Constants constants;

    public DistanceService() {
        this.constants = new Constants();
    }

    public double GetDistanceInKM(Location from, Location to)
    {
        double differenceLatitudeRadian = (to.getLatitude() - from.getLatitude()) * constants.getRadianCoeff();
        double differenceLongitudeRadian = (to.getLongitude() - from.getLongitude()) * constants.getRadianCoeff();

        double startLatitudeRadian = from.getLatitude() * constants.getRadianCoeff();
        double endLatitudeRadian = to.getLatitude() * constants.getRadianCoeff();

        double a = Math.sin(differenceLatitudeRadian / 2) * Math.sin(differenceLatitudeRadian / 2) +
                Math.sin(differenceLongitudeRadian / 2) * Math.sin(differenceLongitudeRadian / 2) *
                        Math.cos(startLatitudeRadian) * Math.cos(endLatitudeRadian);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * constants.getEarthRadiusKm();
    }
}
