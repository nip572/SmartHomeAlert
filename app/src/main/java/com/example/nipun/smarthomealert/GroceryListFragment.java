package com.example.nipun.smarthomealert;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroceryListFragment extends Fragment {


    FireBaseModel fireBaseModel;
    List<GroceryList> groceryList= new ArrayList<GroceryList>();
    ListView lv ;
    private  String userId;
    private AdapterGroceryList adapterGroceryList;
    public GroceryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        //get shared pref
        SharedPreferences sharedPreferencesUid = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = sharedPreferencesUid.getString("userId" , "");


        //Second
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getInstance().getReference(userId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                fireBaseModel = dataSnapshot.getValue(FireBaseModel.class);
                groceryList = fireBaseModel.getGroceryList();
                Log.d(groceryList.get(0).getName(), "onDataChange: grocerlistActivity");

                lv = (ListView) rootView.findViewById(R.id.list_view_ingredient_master);
                adapterGroceryList = new AdapterGroceryList(rootView.getContext() , groceryList);
                lv.setAdapter(adapterGroceryList);
                adapterGroceryList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", "");
            }
        });


        return rootView;
    }

}
