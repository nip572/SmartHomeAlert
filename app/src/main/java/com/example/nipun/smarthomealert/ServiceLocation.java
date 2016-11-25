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
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;

import static android.R.attr.value;
import static com.google.android.gms.common.api.Status.we;
import static java.awt.font.TextAttribute.WEIGHT;

/**
 * Created by Nipun on 11/24/16.
 */

public class ServiceLocation extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    private Integer weightValue;
    private Integer minimumThrehold;
    private String longitude;
    private String latitude;


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
               Double longitudeDouble = location.getLongitude();
                longitude = longitudeDouble.toString();
                Double latitudeDouble = location.getLatitude();
                latitude = latitudeDouble.toString();
                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());

                //if weight value is less than minimum threshold make a rest call to places api get radius of first in list if less that .5 mile send a notification
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


        //for notifcation
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference weightRef = database.getInstance().getReference("Db").child("User1").child("WeightValue");
        DatabaseReference minimumThreholdRef = database.getInstance().getReference("Db").child("User1").child("minimumThreshold");
        minimumThreholdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                minimumThrehold = dataSnapshot.getValue(Integer.class);
                Log.d(minimumThrehold.toString(), "onDataChange: mMTR");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        weightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                weightValue = dataSnapshot.getValue(Integer.class);
                if (weightValue < minimumThrehold) {
                    //Make rest call to places API return true if radius is less than configured radius
                    //If true, Send a notification
                    getNotification();

                }
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

    public void getNotification() {
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
        notificationManager.notify(0, n);

    }
}