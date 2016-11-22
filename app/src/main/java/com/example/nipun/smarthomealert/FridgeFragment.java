package com.example.nipun.smarthomealert;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.value;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {

    TextView milkView;

    public FridgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=  inflater.inflate(R.layout.fragment_fridge, container, false);

        milkView = (TextView) rootView.findViewById(R.id.milk_left);

        //TO DO FIX GETTING THE VALUES BY CREATING MODELS
        FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getInstance().getReference("Db");
                 //myRef= myRef.child("User1").child("Open");
        //myRef.setValue("Hello, World!");
                myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                              // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                                //String value = dataSnapshot.getValue(String.class);
                                        //Log.d( "Value is: " ," " +value);
                            //milkView.setText(value);

                            }

                                @Override
                       public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                        Log.w( "Failed to read value.", "");
                            }
                    });

        return  rootView;
    }

}
