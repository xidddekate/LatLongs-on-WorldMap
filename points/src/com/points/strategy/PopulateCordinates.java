package com.points.strategy;

import com.points.models.Location;
import com.points.service.DistanceService;
import com.points.service.cordinate.IntermediateCordinateService;

import java.util.List;

public class PopulateCordinates {

    private final IntermediateCordinateService intermediateCordinateService;
    private final DistanceService distanceService;

    public PopulateCordinates() {
        this.intermediateCordinateService = new IntermediateCordinateService();
        this.distanceService = new DistanceService();
    }

    public double populateCordinatesWRTJustPreviousLocation(double prevDistance, double intermediateDistance, List<Location>result, Location end, Location begin){
        Location justPrev = begin;
        double distanceLeft = prevDistance + distanceService.GetDistanceInKM(begin,end)*1000;
        while (distanceLeft >= intermediateDistance){
            double bearing = intermediateCordinateService.FindInitialBearing(justPrev,end);
            Location ans = intermediateCordinateService.GetIntermediateLocation(justPrev,bearing,Math.abs(intermediateDistance-prevDistance)/1000);
            justPrev = ans;
            result.add(ans);
            prevDistance = 0;
            distanceLeft = distanceService.GetDistanceInKM(ans,end)*1000;
        }
        return distanceLeft;
    }
}
