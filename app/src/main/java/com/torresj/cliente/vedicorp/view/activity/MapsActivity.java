package com.torresj.cliente.vedicorp.view.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.torresj.cliente.vedicorp.R;

import com.torresj.cliente.vedicorp.databinding.ActivityMapsBinding;
import com.torresj.cliente.vedicorp.utils.EnableServices;
import com.torresj.cliente.vedicorp.utils.FetchURL;
import com.torresj.cliente.vedicorp.utils.PermissionGranted;
import com.torresj.cliente.vedicorp.utils.TaskLoadedCallback;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , TaskLoadedCallback {

    private GoogleMap mMap;
   private ActivityMapsBinding binding;
    LocationManager locationManager;
    RequestQueue request;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    Button getDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDirection = findViewById(R.id.btnGetDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });
        //27.658143,85.3199503
        //27.667491,85.3208583
        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds bounds = builder.include(new LatLng(27.658143, 85.3199503)).build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        height = Math.round(0.45f * height);
        int padding = (int) (width * 0.10);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.moveCamera(cameraUpdate);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11)); //set the zoom level here


   /*      LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName("Av. Bolognesi 234, Santa anita, Lima", 1);

            if (addressList != null) {
                double doubleLat = addressList.get(0).getLatitude();
                double doubleLong = addressList.get(0).getLongitude();

                Log.i("DIRECCION", "laitud==>" + String.valueOf(doubleLat) + " | " + "Longitude: " + String.valueOf(doubleLong));

                request = Volley.newRequestQueue(getApplicationContext());
                if (PermissionGranted.checkPermiso(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {   //Si tiene el permiso de gps

                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {                             //Si el gps esta activo
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        EnableServices enableServices = new EnableServices();
                        enableServices.show(fragmentManager, null);

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        LatLngBounds bounds = builder.include(new LatLng(doubleLat, doubleLong)).build();

                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        height = Math.round(0.45f * height);
                        int padding = (int) (width * 0.10);

                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                        mMap.moveCamera(cameraUpdate);
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14)); //set the zoom level here


                    } else {
                        //Toast.makeText(this,"tienes activar gps",Toast.LENGTH_SHORT).show();

                    }

                } else {

                    PermissionGranted.pedirPermiso(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    */
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


}