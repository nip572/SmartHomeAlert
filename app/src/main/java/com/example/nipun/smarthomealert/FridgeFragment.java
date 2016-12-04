package com.example.nipun.smarthomealert;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.id;
import static android.R.attr.value;
import static com.example.nipun.smarthomealert.R.id.progress_circle_text;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {

    TextView milkView;
    TextView tvOpenClose;
    TextView tvFridge;
    TextView tvFreezer;
    private  String userId;
    private TextView tvMaximumWeight;
    private ProgressBar weightBar;
    private Integer maxWeight;
    private Integer currentWeight;
    View rootView;
    private Integer tempValue;
    private String openClose;
    private TextView tvCircle_text;



    public FridgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=  inflater.inflate(R.layout.fragment_fridge, container, false);
        ;

        //get shared pref
        SharedPreferences sharedPreferencesUid = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = sharedPreferencesUid.getString("userId" , "");
/*


        milkView = (TextView) rootView.findViewById(R.id.milk_left);

        //TO DO FIX GETTING THE VALUES BY CREATING MODELS
        FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getInstance().getReference(userId).child("weightValue");
                 //myRef= myRef.child(userId).child("Open");
        //myRef.setValue("Hello, World!");
                myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                              // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                                Integer value = dataSnapshot.getValue(Integer.class);
                                        //Log.d( "Value is: " ," " +value);
                            milkView.setText(value.toString() + " gms left");

                            }

                                @Override
                       public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                        Log.w( "Failed to read value.", "");
                            }
                    });

                //DOOR STATUS

        tvOpenClose = (TextView) rootView.findViewById(R.id.door_open_close);
        DatabaseReference doorStatusref = database.getInstance().getReference(userId).child("open");
        //myRef= myRef.child(userId).child("Open");
        //myRef.setValue("Hello, World!");
        doorStatusref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvOpenClose.setText(value);
                if(value.equals("Open")){
                    //START TIMER
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", "");
            }
        });


        tvFridge = (TextView) rootView.findViewById(R.id.temperature_fridge);
        tvFreezer = (TextView) rootView.findViewById(R.id.temperature_freezer);

        DatabaseReference temperaturStatusref = database.getInstance().getReference(userId).child("temperatureValue");
        //myRef= myRef.child(userId).child("Open");
        //myRef.setValue("Hello, World!");
        temperaturStatusref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer fridgeValue = dataSnapshot.getValue(Integer.class);
                 tvFridge.setText(fridgeValue.toString() + "°C");
                Integer freezerValue  = fridgeValue - 10;
                tvFreezer.setText(freezerValue.toString() + "°C");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", "");
            }
        });







*/
        //// START AGAIN

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        tvOpenClose = (TextView) rootView.findViewById(R.id.door_open_close);
        tvFridge = (TextView) rootView.findViewById(R.id.temperature_fridge);
        milkView = (TextView) rootView.findViewById(R.id.milk_left);
        tvMaximumWeight = (TextView) rootView.findViewById(R.id.maximum_weight);
        tvCircle_text = (TextView) rootView.findViewById(progress_circle_text);

        DatabaseReference maximumWeightRef = database.getInstance().getReference(userId);
        maximumWeightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                FireBaseModel fireBaseModel = dataSnapshot.getValue(FireBaseModel.class);
                maxWeight = fireBaseModel.getMaximumWeight();
                tvMaximumWeight.setText(maxWeight.toString() + " ml");

                currentWeight = fireBaseModel.getWeightValue();
                milkView.setText(currentWeight.toString());

                tempValue = fireBaseModel.getTemperatureValue();
                tvFridge.setText(tempValue.toString() + "°C");

                openClose = fireBaseModel.getOpen();
                tvOpenClose.setText(openClose);

                Double percentageLeft = Double.valueOf((currentWeight *100)/maxWeight);
                tvCircle_text.setText(percentageLeft.toString());


                weightBar = (ProgressBar) rootView.findViewById(R.id.weightProgressBar);

                calculateWeightStatistics(maxWeight,currentWeight,weightBar);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", "");
            }
        });
        return  rootView;
    }

    public void calculateWeightStatistics(int maxThreshold, int currentValue, ProgressBar bar){
        int value = calculatePercentage(maxThreshold,currentValue);
        bar.setProgress(value);
    }

    public int calculatePercentage(int maxThreshold, int currentValue){
        return (currentValue*100)/maxThreshold;
    }

}
