package com.example.nipun.smarthomealert;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.button;
import static android.R.attr.value;
import static java.security.AccessController.getContext;

public class SettingsActivity extends AppCompatActivity {

    private EditText etMinimumThreshold;
    private EditText etRadius;
    private ToggleButton tbPushNotifictions;
    private ToggleButton tbAutomaticOrder;
    private Button   buttonSave;
    FireBaseModel fireBaseModel;
    String userId;


    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferencesUid = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
         userId= sharedPreferencesUid.getString("userId" , "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference(userId);
        //Getting refrences
        etRadius = (EditText) findViewById( R.id.settings_activity_radius_edit_text);
        //etMinimumThreshold = (EditText) findViewById(R.id.settings_activity_minimum_threshold_edit_text);
        tbPushNotifictions = (ToggleButton) findViewById(R.id.settings_activity_toggle_push_notifications);
        tbAutomaticOrder = (ToggleButton) findViewById(R.id.settings_activity_toggle_button_automatic_order);
        buttonSave = (Button) findViewById( R.id.settings_activity_save_button);


        //PUSH NOTIFICATIONS
        tbPushNotifictions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //ENABLED
                    fireBaseModel.setPushNotifications("true");
                    tbPushNotifictions.setTextColor(Color.parseColor("#008000"));
                } else {
              //DISABLED
                    fireBaseModel.setPushNotifications("false");
                    tbPushNotifictions.setTextColor(Color.parseColor("#ff0000"));

                }
                myRef.setValue(fireBaseModel);
            }
        });

        //AUTOMATIC ORDER
        tbAutomaticOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //ENABLED
                    fireBaseModel.setAutomaticOrder("true");
                    tbAutomaticOrder.setTextColor(Color.parseColor("#008000"));
                } else {
                    //DISABLED
                    fireBaseModel.setAutomaticOrder("false");
                    tbAutomaticOrder.setTextColor(Color.parseColor("#ff0000"));

                }
                myRef.setValue(fireBaseModel);
            }
        });


        //SAVE BUTTON ON CLICK LISTENER
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            fireBaseModel.setRadius(Integer.parseInt(etRadius.getText().toString()));
                //fireBaseModel.setMinimumThreshold(Integer.parseInt(etMinimumThreshold.getText().toString()));
            myRef.setValue(fireBaseModel);
            }
        });


        //ON DATA CHANGE
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            fireBaseModel = dataSnapshot.getValue( FireBaseModel.class);
                Log.d(fireBaseModel.getAddress(), "onDataChange: CHECKING VALUE");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", "");
            }
        });
    }
}
