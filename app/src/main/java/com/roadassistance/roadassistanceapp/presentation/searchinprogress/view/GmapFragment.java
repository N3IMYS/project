package com.roadassistance.roadassistanceapp.presentation.searchinprogress.view;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roadassistance.roadassistanceapp.R;

import java.util.zip.Inflater;

/**
 * Created by daniel on 09.03.2018.
 */

public class GmapFragment extends Fragment implements OnMapReadyCallback, IGps {
    Gps mGps;
    GoogleMap gMap;
    CameraPosition position;
    MapView mMapView;
    View mView;
    Context context;
    private Boolean locationPermissionGranted = false;
    private static final int PERMISSION_REQUEST_CODE = 0x01;

    public GmapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_gmaps, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.gmaps);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context = getActivity();
        getLocationPermissions();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MapsInitializer.initialize(getContext());
        }
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            getLocationPermissions();
            return;
        }
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        if (getMyLocation() != null) {
            Location myLocation = getMyLocation();
            Marker markerNow = addCustomMarker(myLocation.getLatitude(), myLocation.getLongitude(),
                    gMap, "location_1", 40, 40, false);
            markerNow.setTag("my_location");
            markerNow.setRotation(90);

            position = CameraPosition
                    .builder().target(markerNow.getPosition())
                    .zoom(13).bearing(0).tilt(0).build();
            gMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            Toast toast = Toast.makeText(context, "located_by_gps", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "--------", Toast.LENGTH_SHORT);
            toast.show();

        }
       /* Marker marker = gMap.addMarker(new MarkerOptions().
                position(new LatLng(-10, 10)).
                icon((BitmapDescriptorFactory.
                        fromBitmap(resizeMapIcons("location_1", 40, 40)))));

        position = CameraPosition
                .builder().target(marker.getPosition()).zoom(13).bearing(0).tilt(0).build();
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));*/
    }

    private Marker addCustomMarker(double lat, double lng, GoogleMap gMap,
                                   String iconName, int width, int height, boolean isFlat) {
        LatLng ltlng = new LatLng(lat, lng);
        Marker marker = gMap.addMarker(new MarkerOptions().position(ltlng)
                .icon((BitmapDescriptorFactory.fromBitmap(resizeMapIcons(iconName, width, height))))
                .flat(isFlat));
        if (iconName == null) {
            Marker marker1 = gMap.addMarker(new MarkerOptions().position(ltlng)
                    .flat(isFlat));
            return marker1;
        }
        return marker;
    }


    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap =
                BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", context.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public void getLocationPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
            }

        } else {
            ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionGranted = false;
                            return;
                        }
                    }
                    locationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    public Location getMyLocation() {
        mGps = new Gps(context);
        return mGps.getLocation();
    }
}
