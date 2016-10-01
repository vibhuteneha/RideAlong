package com.ridealong.Modules;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Neha on 5/30/2016.
 */
public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}