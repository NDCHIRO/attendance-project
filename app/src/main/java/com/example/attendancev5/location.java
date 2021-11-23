package com.example.attendancev5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancev5.ui.DashBoard;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.os.Handler;
public class location extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationClient;
    //fixed location 30.116535798310395, 31.32037826067701
    double fixedLat = 30.11654;
    double fixedLong = 31.32037826067701;
    double latCurrent;
    double longCurrent;
    double Distance;
    Button btnLoc;
    TextView latLocation;
    TextView longLocation;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mail = bundle.getString("mail");
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(location.this);

        latLocation = findViewById(R.id.latTextView);
        longLocation = findViewById(R.id.longTextView);
        btnLoc = findViewById(R.id.locationbutton);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(location.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)

                    getCurrentLocation();
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER ))
        {
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location loc=task.getResult();
                    if(loc!=null)
                    {
                        latCurrent=loc.getLatitude();
                        longCurrent=loc.getLongitude();
                        System.out.println("hallo "+latCurrent+"");
                        System.out.println(longCurrent+"");
                        latLocation.setText(latCurrent+"");
                        longLocation.setText(longCurrent+"");
                        latLocation.setTextColor(Color.DKGRAY);
                        longLocation.setTextColor(Color.DKGRAY);
                        if(Math.abs(fixedLat-latCurrent)<0.005
                        && Math.abs(fixedLong-longCurrent)<0.005)
                        {
                            gotToQRScanner();
                        }
                        //if person is far from location
                        else {Toast.makeText(location.this, "you are so far from the location", Toast.LENGTH_LONG).show();}
                    }
                    else
                    {
                        Toast.makeText(location.this, "Permission Not Granted", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void gotToQRScanner() {
        Toast.makeText(this, "you are in the right place", Toast.LENGTH_LONG).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent QRScannerIntent = new Intent(location.this, QRScanner.class);
                QRScannerIntent.putExtra("mail", mail);
                startActivity(QRScannerIntent);
            }
        }, 2000);

    }

   /* private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location loc = task.getResult();
                if (loc != null) {
                    try {
                        Geocoder geocoder = new Geocoder(location.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                loc.getLatitude(), loc.getLongitude(), 1);

                        latLocation.setText(addresses.get(0).getLatitude()+"");
                        longLocation.setText(addresses.get(0).getLongitude()+"");
                        latLocation.setTextColor(Color.YELLOW);
                        longLocation.setTextColor(Color.YELLOW);
                        System.out.println("hallo "+latCurrent+"");
                        System.out.println(longCurrent+"");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }*/


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}