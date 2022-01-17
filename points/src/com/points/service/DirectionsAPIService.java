package com.points.service;


import com.points.models.Constants;
import com.points.models.Location;
import com.points.models.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DirectionsAPIService {
    private final Location from;
    private final Location to;
    HttpURLConnection conn;
    Constants constants;

    public DirectionsAPIService(Location from, Location to) {
        this.from = from;
        this.to = to;
        this.constants = new Constants();
    }

    public List<Route> getDataFromDirectionsAPI(){
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try{
            String apiUrl = "https://maps.googleapis.com/maps/api/directions/json?" + "origin=" + from.getLatitude() + "," + from.getLongitude() + "&destination=" + to.getLatitude() + "," + to.getLongitude() + "&key=" + constants.getAPIKEY();
            URL url = new URL(apiUrl);

            conn = (HttpURLConnection) url.openConnection();

            // Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return parse(responseContent.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return new ArrayList<>();
    }

    public static List<Route> parse(String responseBody) {
        JSONObject response = new JSONObject(responseBody);
        JSONArray routes = new JSONArray(response.getJSONArray("routes"));
        JSONObject firstRoute = routes.getJSONObject(0);
        JSONArray legs = new JSONArray(firstRoute.getJSONArray("legs"));
        JSONObject firstLeg = legs.getJSONObject(0);
        JSONArray steps = new JSONArray(firstLeg.getJSONArray("steps"));

        List<Route> allRoutes = new ArrayList<>();
        for (int i=0;i<steps.length();i++){
            JSONObject step = steps.getJSONObject(i);
            JSONObject distance = step.getJSONObject("distance");
            JSONObject start_location = step.getJSONObject("start_location");
            JSONObject end_location = step.getJSONObject("end_location");
            JSONObject polyline = step.getJSONObject("polyline");
            Route route = new Route(
                                distance.getInt("value"),
                                new Location(start_location.getDouble("lat"),start_location.getDouble("lng")),
                                new Location(end_location.getDouble("lat"),end_location.getDouble("lng")),
                                polyline.getString("points")
                            );
            allRoutes.add(route);
        }
        return allRoutes;
    }
}
