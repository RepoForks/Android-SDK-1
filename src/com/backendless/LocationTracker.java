/*
 * ********************************************************************************************************************
 *  <p/>
 *  BACKENDLESS.COM CONFIDENTIAL
 *  <p/>
 *  ********************************************************************************************************************
 *  <p/>
 *  Copyright 2012 BACKENDLESS.COM. All Rights Reserved.
 *  <p/>
 *  NOTICE: All information contained herein is, and remains the property of Backendless.com and its suppliers,
 *  if any. The intellectual and technical concepts contained herein are proprietary to Backendless.com and its
 *  suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret
 *  or copyright law. Dissemination of this information or reproduction of this material is strictly forbidden
 *  unless prior written permission is obtained from Backendless.com.
 *  <p/>
 *  ********************************************************************************************************************
 */

package com.backendless;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by julia
 */
public class LocationTracker implements LocationListener {
    private final int MIN_TIME = 60 * 1000; // 1 minute
    private final int MIN_DISTANCE = 10; // meters

    private final LocationManager locationManager;
    private final Map<String, BackendlessLocationListener> locationListeners;
    private String provider;
    private static LocationTracker instance;

    private LocationTracker() {
        Context context = ((AndroidService) AndroidService.recoverService()).getApplicationContext();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListeners = new HashMap<String, BackendlessLocationListener>();
    }

    public static LocationTracker getInstance() {
        if (instance == null) {
            instance = new LocationTracker();
        }
        return instance;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            locationChanged(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        listenBestProvider();
    }

    @Override
    public void onProviderEnabled(String s) {
        listenBestProvider();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (s.equals(provider)) {
            listenBestProvider();
        }
    }

    public boolean isContainListener(String name) {
        return locationListeners.containsKey(name);
    }

    public void addListeners(String name, BackendlessLocationListener locationListener) {
        this.locationListeners.put(name, locationListener);

        firstListen(locationListener);
    }

    public void removeListener(String name) {
        locationListeners.remove(name);
        if (locationListeners.size() == 0) {
            locationManager.removeUpdates(this);
        }
    }

    private void listenBestProvider() {
        String bestProvider = locationManager.getBestProvider(new Criteria(), true);
        if (!bestProvider.equals(provider)) {
            listenProvider(bestProvider);
        }
    }

    private void listenProvider(String provider) {
        this.provider = provider;
        locationManager.removeUpdates(this);
        locationManager.requestLocationUpdates(this.provider, MIN_TIME, MIN_DISTANCE, this);
    }

    private void firstListen(BackendlessLocationListener locationListener) {
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null)
            locationListener.onLocationChanged(location);
    }

    private void locationChanged(Location location) {
        for (String name : locationListeners.keySet()) {
            locationListeners.get(name).onLocationChanged(location);
        }
    }
}
