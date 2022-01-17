package com.points.models;

public class Route {
    private final int distanceInMeters;
    private final Location start;
    private final Location end;
    private final String polyline;

    public int getDistanceInMeters() {
        return distanceInMeters;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public String getPolyline() {
        return polyline;
    }

    public Route(int distanceInMeters, Location start, Location end, String polyline) {
        this.distanceInMeters = distanceInMeters;
        this.start = start;
        this.end = end;
        this.polyline = polyline;
    }
}
