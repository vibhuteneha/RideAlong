package com.ridealong.Modules;

import java.util.List;

/**
 * Created by Neha on 5/30/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}