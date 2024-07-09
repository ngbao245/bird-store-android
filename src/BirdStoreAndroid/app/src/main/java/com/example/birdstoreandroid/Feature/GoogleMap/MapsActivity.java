package com.example.birdstoreandroid.Feature.GoogleMap;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.birdstoreandroid.Feature.GetProduct.GetProductActivity;
import com.example.birdstoreandroid.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.birdstoreandroid.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    public static final int LOCATION_PERMISSION_CODE = 100;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double currentLat, currentLong;

    private SearchView searchBar;
    private LatLng destination;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        initializeViews();
        setUpListeners();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        requestUserPermission();
    }

    private void initializeViews() {
        btnCalculate = findViewById(R.id.btnCalculation);
        searchBar = findViewById(R.id.search_bar);
    }

    private void setUpListeners() {
        btnCalculate.setOnClickListener(view -> {
            if (destination != null) {
                fetchAndReturnAddress();
            } else {
                showToast("No location selected");
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void fetchAndReturnAddress() {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(destination.latitude, destination.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressDetails = address.getAddressLine(0);

                // Calculate the distance
                float[] results = new float[1];
                Location.distanceBetween(currentLat, currentLong, destination.latitude, destination.longitude, results);
                float distanceInKm = results[0] / 1000;

                // Store in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("location_distance", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userLocation", addressDetails);
                editor.putFloat("userDistance", distanceInKm);
                editor.apply();

                Intent intent = new Intent();
                intent.putExtra("address", addressDetails);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                showToast("Unable to get address details");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Geocoder service failed");
        }
    }

    private void searchLocation(String locationName) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
            if (!addressList.isEmpty()) {
                Address address = addressList.get(0);
                destination = new LatLng(address.getLatitude(), address.getLongitude());
                updateMapWithMarker(destination, locationName);
            } else {
                showToast("Location not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error finding location");
        }
    }

    private void updateMapWithMarker(LatLng latLng, String title) {
        if (mMap != null) {
            mMap.clear();
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }

    private void requestUserPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
            fetchUserLocation();
        }
    }

    private void fetchUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLat = location.getLatitude();
                currentLong = location.getLongitude();
                LatLng userLocation = new LatLng(currentLat, currentLong);
                updateMapWithMarker(userLocation, "Your current location");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permission granted!");
                fetchUserLocation();
            } else {
                showToast("Location permission denied, please allow permission to access location");
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
