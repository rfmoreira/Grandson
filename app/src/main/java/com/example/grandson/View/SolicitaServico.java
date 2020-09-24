package com.example.grandson.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.grandson.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class SolicitaServico extends FragmentActivity {


    private SupportMapFragment supportMapFragment;
    private Location location;
    private LocationManager locationManager;
    private FusedLocationProviderClient client;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita_servico);


        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        client = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getCurrentLocation();

    }


    private void getCurrentLocation() {
        Log.i("getCurrent", "Entrou");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ) {
            Log.i("getCurrent", "Entrou no if");
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location loc) {
                         location =loc;
                    if(location != null ){
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                //Log.i("getCurrent","Entrou no suport  "+location.getLatitude());
                                LatLng latLng = new LatLng(location.getLatitude()
                                        ,location.getLongitude());
                                try {
                                    address = getAddress(location.getLatitude(),location.getLongitude());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                MarkerOptions options = new MarkerOptions().position(latLng).title(address.getAddressLine(0));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                                googleMap.isMyLocationEnabled();
                                googleMap.addMarker(options);
                            }
                        });
                    }
                }
            });
        }else {
           /* if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }else{**/
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        44);
                Log.i("getCurrent","Entrou no else");
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }


    public Address getAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(getApplicationContext());
        Address address = null;
        List<Address> addresses;

        addresses = geocoder.getFromLocation(latitude,longitude,1);
        if (addresses.size() >0){
                address = addresses.get(0);
        }
        Toast.makeText(this, "Address: " + address.getCountryName()
                +' '+ address.getFeatureName()
                +' '+ address.getAdminArea()
                +' '+ address.getAddressLine(0)

                , Toast.LENGTH_SHORT).show();
        Log.i("getCurrent","Address: " + address.getCountryName()
                +' '+ address.getFeatureName()
                +' '+ address.getAdminArea()
                +' '+ address.getAddressLine(0));
        return address;
    };

    @Override
    protected void onResume() {
        super.onResume();
       // getCurrentLocation();
        supportMapFragment.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        supportMapFragment.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //getCurrentLocation();
        supportMapFragment.onPause();

    }



}