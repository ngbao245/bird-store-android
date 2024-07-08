package com.example.birdstoreandroid.Feature.GoogleMap;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdstoreandroid.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.birdstoreandroid.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;


    public static int LOCATION_PERMISSION_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat, currentLong;

    SearchView search_bar;

    LatLng getDestination;
    Marker destinationMarker;

    LatLng destinationLatLng;
    Button btnCalculate;
    TextView txtDistance;
    LatLng userLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnCalculate = findViewById(R.id.btnCalculation);
        btnCalculate.setOnClickListener(view -> calculateDistanceToDestination());

        txtDistance = findViewById(R.id.txtDistance);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getUserPermission();


        search_bar = (SearchView) findViewById(R.id.search_bar);
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String getLocationInput = search_bar.getQuery().toString();

                if (getLocationInput == null) {
                    Toast.makeText(MapsActivity.this, "Location not found!", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);

                    try {
                        List<Address> addressList = geocoder.getFromLocationName(getLocationInput, 1);

                        if (addressList.size() > 0) {
                            getDestination = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

                            if (destinationMarker != null) {
                                destinationMarker.remove();
                            }

                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(getDestination).title(getLocationInput));
                            MarkerOptions markerOptions = new MarkerOptions().position(getDestination).title(getLocationInput);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getDestination, 10);
                            mMap.animateCamera(cameraUpdate);
                            destinationMarker = mMap.addMarker(markerOptions);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private void calculateDistanceToDestination() {
        String destination = search_bar.getQuery().toString();

        destinationLatLng = getDestinationAddress(destination);
        if (destinationLatLng != null) {
            float distance = calculateDistance(userLocation, getDestination);
            if ((distance / 1000) < 1) {
                String formattedDistance = String.format("%.2f", distance);
                txtDistance.setText(formattedDistance + " meters");
            } else {
                String formattedDistance = String.format("%.2f", distance / 1000);
                txtDistance.setText(formattedDistance + " kilometers");
            }
        } else {
            Toast.makeText(MapsActivity.this, " Cannot define address", Toast.LENGTH_SHORT).show();
        }
    }

    private LatLng getDestinationAddress(String destinationAddress) {
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(destinationAddress, 5);
            if (addressList == null) {
                return null;
            }
            Address location = addressList.get(0);
            return new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private float calculateDistance(LatLng start, LatLng end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end locations must not be null");
        }

        Location startLocation = new Location("");
        startLocation.setLatitude(start.latitude);
        startLocation.setLongitude(start.longitude);

        Location endLocation = new Location("");
        endLocation.setLatitude(end.latitude);
        endLocation.setLongitude(end.longitude);
        return startLocation.distanceTo(endLocation);
    }


    private void getUserPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // return location getUserLocation() if the permissionResult is granted.
            getUserLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLat = location.getLatitude();
                currentLong = location.getLongitude();

                userLocation = new LatLng(currentLat, currentLong);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 100);
                mMap.animateCamera(cameraUpdate);

                MarkerOptions markerOptions = new MarkerOptions().position(userLocation).title("Your current location");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                mMap.addMarker(markerOptions);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Location Permission has been denied, please allow permission to access location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(true);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}