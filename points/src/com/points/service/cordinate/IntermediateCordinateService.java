package com.points.service.cordinate;

import com.points.models.Constants;
import com.points.models.Location;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class IntermediateCordinateService {
    private final Constants constants;

    public IntermediateCordinateService() {
        this.constants = new Constants();
    }

    public double FindInitialBearing(Location from, Location to)
    {
        if (from == null || to == null)
        {
            return 0;
        }

        double fromLatRadian = from.getLatitude() * constants.getRadianCoeff();
        double toLatRadian = to.getLatitude() * constants.getRadianCoeff();
        double longitudeDeltaRadian = (to.getLongitude() - from.getLongitude()) * constants.getRadianCoeff();

        double x = Math.cos(fromLatRadian) * Math.sin(toLatRadian)
                - Math.sin(fromLatRadian) * Math.cos(toLatRadian) * Math.cos(longitudeDeltaRadian);
        double y = Math.sin(longitudeDeltaRadian) * Math.cos(toLatRadian);
        double t = Math.atan2(y, x);

        double bearing = t * constants.getDegreesCoeff();

        return Wrap360(bearing);
    }

    public Location GetIntermediateLocation(Location from, double bearing, double distance)
    {
        // https://www.movable-type.co.uk/scripts/latlong.html
        if (from == null)
        {
            return null;
        }
        return FindDestinationForGivenStartPointAndBearing(from, bearing, distance);
    }

    private Location FindDestinationForGivenStartPointAndBearing(Location from, double bearing, double distance)
    {
        if (from == null)
        {
            return null;
        }

        double angular = distance / constants.getEarthRadiusKm();
        double bearingRadian = bearing * constants.getRadianCoeff();
        double latitudeRadian = from.getLatitude() * constants.getRadianCoeff();
        double longitudeRadian = from.getLongitude() * constants.getRadianCoeff();

        double destLatSine = Math.sin(latitudeRadian) * Math.cos(angular)
                + Math.cos(latitudeRadian) * Math.sin(angular) * Math.cos(bearingRadian);
        double destLatitudeRadian = Math.asin(destLatSine);

        double y = Math.sin(bearingRadian) * Math.sin(angular) * Math.cos(latitudeRadian);
        double x = Math.cos(angular) - Math.sin(latitudeRadian) * destLatSine;
        double destLongitudeRadian = longitudeRadian + Math.atan2(y, x);

        double degreesCoeff = 180 / Math.PI;
        double destLatitude = destLatitudeRadian * degreesCoeff;
        double destLongitude = destLongitudeRadian * degreesCoeff;

        DecimalFormat df = new DecimalFormat("#.######");
        df.setRoundingMode(RoundingMode.CEILING);

        return new Location(
                Double.parseDouble(df.format(Wrap90(destLatitude))),
                Double.parseDouble(df.format(Wrap180(destLongitude)))
        );
    }

    private double Wrap360(double degrees)
    {
        if (0 <= degrees && degrees < 360)
        {
            return degrees;
        }
        return (degrees % 360 + 360) % 360;
    }

    private double Wrap180(double degrees)
    {
        if (-180 < degrees && degrees <= 180)
        {
            return degrees;
        }
        return (degrees + 540) % 360 - 180;
    }

    private double Wrap90(double degrees)
    {
        if (-90 <= degrees && degrees <= 90)
        {
            return degrees;
        }
        return Math.abs((degrees % 360 + 270) % 360 - 180) - 90;
    }
}
