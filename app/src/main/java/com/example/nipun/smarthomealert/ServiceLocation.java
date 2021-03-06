package com.example.nipun.smarthomealert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.Toast;
import static java.util.concurrent.TimeUnit.*;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.angle;
import static android.R.attr.gravity;
import static android.R.attr.radius;
import static android.os.Build.RADIO;
import static com.example.nipun.smarthomealert.BaseActivity.gl;
import static java.security.AccessController.getContext;

/**
 * Created by Nipun on 11/24/16.
 */

public class ServiceLocation extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    private Integer weightValue = 0;
    private Integer minimumThrehold= 0;
    private RestCalls restCalls;
    private String currentlocation = "37.338208,-121.886329";
    private Double currentLat = 37.338208;
    private Double radiusFirebase = 2.0;
    private Double currentLong = -121.886329;
    private PlaceApiModel details;
    private  int notificationId = 0;
    private String pushNotifications = "true";
    private String userId;
    private String openClose;
    Intent mapIntent1;
    private Double distanceBetweenLocationAndStore = 5.0;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private List<GroceryList> gl1 = new ArrayList<>();
    private FireBaseModel fireBaseModel;
    private  String resultName;
    GroceryList groceryItem ;
    FireBaseModel fbm;
    Timer timer = new Timer();
    Boolean openCloseBool;

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {

        SharedPreferences sharedPreferencesUid = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = sharedPreferencesUid.getString("userId" , "");



        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
                currentlocation = location.getLatitude() + ","+ location.getLongitude();
                currentLat = location.getLatitude();
                currentLong= location.getLongitude();
                Log.d(currentLat + " " + currentLong, "onLocationChanged: location update");

                //Prepare share preferance
                SharedPreferences sharedPreferencesLat = getSharedPreferences("Lat", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorLat = sharedPreferencesLat.edit();
                editorLat.putString("currentLat" , currentLat.toString());
                editorLat.putString("currentLong" , currentLong.toString());
                editorLat.apply();

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


     mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                Log.d("SHAKE", "onShake: SHAKE");
                try{
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    locationManager.removeUpdates(listener);

                }
                catch (SecurityException e){
                    Log.d("Not given permission", "onCreate: Security exception");
                }


            }

        });


        //PUSH NOTIFICATION ON OR OFF
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        userId = sharedPreferencesUid.getString("userId" , "");
        DatabaseReference pushNotificationsRef = database1.getInstance().getReference(userId).child("pushNotifications");
        pushNotificationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               pushNotifications = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        //RADIUS REFEREMCE
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference radiusRef = database.getInstance().getReference(userId).child("radius");
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

        DatabaseReference weightRef = database.getInstance().getReference(userId).child("weightValue");
        weightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                weightValue= dataSnapshot.getValue(Integer.class);

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
                        resultName = details.getResults().get(0).getName();
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
                if(pushNotifications.equals("true")){
                if (weightValue != 0 && weightValue < minimumThrehold) {

                    //getData

                    if(distanceBetweenLocationAndStore < radiusFirebase ){
                        getNotificationDistanceIsLess();
                    }
                    else {
                        getNotificationWeightIsLess();
                    }

                }
                    }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference minimumThreholdRef = database.getInstance().getReference(userId).child("minimumThreshold");
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



            //OPEN CLOSE REF
        DatabaseReference openCloseRef = database.getInstance().getReference(userId).child("open");
        openCloseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                openClose = dataSnapshot.getValue(String.class);

                if(openClose.equals("Open")){

                    int MINUTES = 1; // The delay in minutes


                      timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("start");// / Function runs every MINUTES minutes.
                            getDoorIsOpen();

                        }
                    }, 0, 1000 * 60 * MINUTES);
                }
                else{

                    timer.cancel();
                    timer = new Timer();
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }



   //



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
                .setSmallIcon(R.drawable.app_logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(++notificationId, n);

    }// END NOTIFICATION WEIGHT IS LESS

    public void getNotificationDistanceIsLess() {
        Intent intent1 = new Intent(this, ServiceLocation.class);


        //startActivity(mapIntent1);

        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        //PendingIntent intentNavig = PendingIntent.getActivity(this ,(int) System.currentTimeMillis() , mapIntent1 ,0);
        Notification n = new Notification.Builder(this)
                .setContentTitle("You are near " + resultName)
                .setContentText("Milk is low")
                .setSmallIcon(R.drawable.app_logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_menu_send, "Drive", getMapNavigationIntent(mapIntent1 , 'q'))
                .addAction(R.drawable.ic_menu_send, "Walk", getMapNavigationIntent(mapIntent1 , 'w'))
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);

    }

    public void getDoorIsOpen() {
        Intent intent1 = new Intent(this, ServiceLocation.class);


        //startActivity(mapIntent1);

        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        //PendingIntent intentNavig = PendingIntent.getActivity(this ,(int) System.currentTimeMillis() , mapIntent1 ,0);
        Notification n = new Notification.Builder(this)
                .setContentTitle("Door is Open")
                .setContentText("Reminder to close the door")
                .setSmallIcon(R.drawable.app_logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);

    }







    public PendingIntent getMapNavigationIntent (Intent intent , char c){
        // BUILD MAP INTENT
        Uri gmmIntentUri = Uri.parse("google.navigation:" +c +"="+currentLat.toString()+","+currentLong.toString());
        intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intentNavigation = PendingIntent.getActivity(this ,(int) System.currentTimeMillis() , intent ,0);
        return  intentNavigation;
    }

    private Double distance(double lat1, double lon1, double lat2, double lon2) {

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (float) (earthRadius * c);

        return (dist*.416)/10000000;     }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

        return START_STICKY;
    }


    public void addAutomatic(String userId){
        FirebaseDatabase database4 = FirebaseDatabase.getInstance();
        final DatabaseReference groceryListRef = database4.getInstance().getReference(userId);
       groceryListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fireBaseModel = dataSnapshot.getValue(FireBaseModel.class);


            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        groceryItem = new GroceryList();
        if(fireBaseModel.getGroceryList() != null){
        gl1  = fireBaseModel.getGroceryList();}
        groceryItem.setName("Milk");
        groceryItem.setImageURL("http://www.maryvancenc.com/wp-content/uploads/2013/05/url2.jpeg");

        gl1.add(groceryItem);
        fbm = new FireBaseModel();
        fbm.setGroceryList(gl1);
        groceryListRef.child("groceryList").setValue(gl1);
    }

}