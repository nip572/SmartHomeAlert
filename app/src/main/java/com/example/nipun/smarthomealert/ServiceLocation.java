package com.example.nipun.smarthomealert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.radius;

/**
 * Created by Nipun on 11/24/16.
 */

public class ServiceLocation extends Service  {

    private LocationListener listener;
    private LocationManager locationManager;
    private Integer weightValue;
    private Integer minimumThrehold;
    private RestCalls restCalls;
    private String currentlocation = "37.338208,-121.886329";
    private Double currentLat = 37.338208;
    private Double radiusFirebase = 2.0;
    private Double currentLong = -121.886329;
    private PlaceApiModel details;
    private  int notificationId = 0;
    private Double distanceBetweenLocationAndStore = 5.0;

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {



        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
                currentlocation = location.getLatitude() + ","+ location.getLongitude();
                currentLat = location.getLatitude();
                currentLong= location.getLongitude();
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);


        //RADIUS REFEREMCE
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference radiusRef = database.getInstance().getReference("Db").child("User1").child("Radius");
        radiusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               radiusFirebase = dataSnapshot.getValue(Double.class);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });




        DatabaseReference weightRef = database.getInstance().getReference("Db").child("User1").child("WeightValue");
        weightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                weightValue = dataSnapshot.getValue(Integer.class);
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                restCalls = retrofit.create(RestCalls.class);
                final Call<PlaceApiModel> placeApiModelResponse = restCalls.getPlaceDetails(currentlocation);
                placeApiModelResponse.enqueue(new Callback<PlaceApiModel>() {

                    @Override
                    public void onResponse(Call<PlaceApiModel> call, Response<PlaceApiModel> response) {
                        int statusCode = response.code();
                        details = response.body();
                        String resultName = details.getResults().get(0).getName();
                        Log.d(resultName, "onResponse: NAME OF GROCERY STORE IS");
                        Double groceryStoreLat = details.getResults().get(0).getGeometry().getLocation().getLatitude();
                        Double groceryStoreLong = details.getResults().get(0).getGeometry().getLocation().getLongitude();
                        Log.d(distance(groceryStoreLat , groceryStoreLong , currentLat ,currentLong).toString(), "onResponse: DISTANCE IS ");
                        distanceBetweenLocationAndStore = distance(groceryStoreLat , groceryStoreLong , currentLat ,currentLong);


                    }
                    @Override
                    public void onFailure(Call<PlaceApiModel> call, Throwable t) {

                    }
                });

                if (weightValue < minimumThrehold) {

                    if(distanceBetweenLocationAndStore < radiusFirebase ){
                        getNotificationDistanceIsLess();
                    }
                    else {
                        getNotificationWeightIsLess();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        // DatabaseReference weightRef = database.getInstance().getReference("Db").child("User1").child("WeightValue");
        DatabaseReference minimumThreholdRef = database1.getInstance().getReference("Db").child("User1").child("minimumThreshold");
        minimumThreholdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                minimumThrehold = dataSnapshot.getValue(Integer.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    public void getNotificationWeightIsLess() {
        Intent intent1 = new Intent(this, ServiceLocation.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        Notification n = new Notification.Builder(this)
                .setContentTitle("Weight Value is Low")
                .setContentText("Add milk to list")
                .setSmallIcon(R.drawable.ic_menu_send)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(++notificationId, n);

    }// END NOTIFICATION WEIGHT IS LESS

    public void getNotificationDistanceIsLess() {
        Intent intent1 = new Intent(this, ServiceLocation.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        Notification n = new Notification.Builder(this)
                .setContentTitle("You are near a grocery store")
                .setContentText("Milk is low")
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(++notificationId, n);

    }



    private Double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return ((dist) /1.6) * 0.0004233;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}