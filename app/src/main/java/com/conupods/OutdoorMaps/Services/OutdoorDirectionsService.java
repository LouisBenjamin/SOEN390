package com.conupods.OutdoorMaps.Services;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.List;


public class OutdoorDirectionsService {

    private GoogleMap mMap;
    private GeoApiContext gac;

    private Activity passedActivity;

    public OutdoorDirectionsService(Activity activity, GoogleMap mapFragment) {
        passedActivity = activity;

        gac = new GeoApiContext.Builder()
                .apiKey(activity.getString(R.string.google_maps_key))
                .build();

        mMap = mapFragment;
    }

    private void computeDirections(LatLng origin, LatLng destination, TravelMode mode) {

        // this sends the request to the api
        DirectionsApiRequest directions = new DirectionsApiRequest(gac);

        // to see other options
        //directions.alternatives(true);

        directions.origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude));
        directions.destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude));

        directions.mode(mode);

        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {

                // this just shows the things you have to work with
//                DirectionsRoute[] dr = result.routes;
//
//                DirectionsLeg[] dl = dr[0].legs;
//
//                DirectionsStep[] ds = dl[0].steps;
//
//                String summary = dr[0].summary;

                //addPolyLinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Toast.makeText(passedActivity, "Could not get directions (API failure)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // polyline function - can be modified to work with a single route
    private void addPolyLinesToMap(final DirectionsResult result) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (DirectionsRoute route: result.routes) {
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();
                    for (com.google.maps.model.LatLng latLng: decodedPath) {
                        newDecodedPath.add(new LatLng(latLng.lat, latLng.lng));
                    }

                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                }
            }
        });
    }
}