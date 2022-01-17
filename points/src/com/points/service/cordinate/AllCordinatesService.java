package com.points.service.cordinate;

import com.points.models.Location;
import com.points.models.Route;
import com.points.service.DirectionsAPIService;
import com.points.service.DistanceService;
import com.points.service.PolylineService;
import com.points.strategy.PopulateCordinates;

import java.util.ArrayList;
import java.util.List;

public class AllCordinatesService {
    private final DistanceService distanceService;
    private final PolylineService polylineService;
    private final DirectionsAPIService directionsAPIService;
    private final PopulateCordinates populateCordinates;
    private final Location from;
    private final Location to;

    public AllCordinatesService(double lat1, double lat2, double lon1, double lon2) {
        this.distanceService = new DistanceService();
        this.polylineService = new PolylineService();
        this.from  = new Location(lat1,lon1);
        this.to  = new Location(lat2,lon2);
        this.directionsAPIService = new DirectionsAPIService(from,to);
        this.populateCordinates = new PopulateCordinates();
    }

    public List<Location> getAllCordinates(double intermediateDistance){
        List<Route> routes = directionsAPIService.getDataFromDirectionsAPI();
        List<Location> result = new ArrayList<>();
        double prevRouteLeftOverDistance = 0.0;
        result.add(from);
        for(Route route : routes){
            List<Location> polylineLocations = polylineService.getCordinatesFromPolyline(route.getPolyline());
            for(int index=1;index<polylineLocations.size();index++){
                Location currLocation = polylineLocations.get(index);
                double currDist = distanceService.GetDistanceInKM(polylineLocations.get(index-1),currLocation)*1000;
                if(currDist + prevRouteLeftOverDistance == intermediateDistance){
                    result.add(currLocation);
                    prevRouteLeftOverDistance = 0.0;
                }
                else if(currDist + prevRouteLeftOverDistance > intermediateDistance){
                    Location justPrev = polylineLocations.get(index-1);
                    prevRouteLeftOverDistance = populateCordinates.populateCordinatesWRTJustPreviousLocation(prevRouteLeftOverDistance,intermediateDistance,result,currLocation,justPrev);
                }
                else{
                    prevRouteLeftOverDistance += currDist;
                }
            }
        }
        result.add(to);
        return result;
    }
}
