package com.points;

import com.points.models.Location;
import com.points.service.cordinate.AllCordinatesService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("\n----- Welcome to the Intermediate Co-ordinates Calculator by SUDHANSHU DEKATE -----\n");
        while(true) {
            Scanner scn = new Scanner(System.in);
            System.out.println("Enter SOURCE Latitude and Longitudes separated by space : ");
            double lat1 = scn.nextDouble();
            double lon1 = scn.nextDouble();
            System.out.println("Enter DESTINATION Latitude and Longitudes separated by space : ");
            double lat2 = scn.nextDouble();
            double lon2 = scn.nextDouble();
            System.out.println("Enter distance(in metres) the consecutive points should be apart : ");
            double distance = scn.nextDouble();

            AllCordinatesService allCordinatesService = new AllCordinatesService(lat1, lat2, lon1, lon2);
            List<Location> ans = allCordinatesService.getAllCordinates(distance);
            System.out.println("Intermediate Cordinates are : ");
            for (Location location : ans) {
                System.out.println(location.getLatitude() + "," + location.getLongitude() + ",");
            }
            System.out.println("----------------------------\n");
        }
    }
}
