package com.example.nipun.smarthomealert;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.R.id.list;
import static android.media.CamcorderProfile.get;
import static android.os.Build.VERSION_CODES.M;
import static com.example.nipun.smarthomealert.BaseActivity.gl;
import static java.util.Collections.addAll;


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


     View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        rootView = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        groceryList = BaseActivity.gl;
        ArrayList<Integer> ref = new ArrayList<>();

        HashSet<GroceryList> hs = new HashSet<GroceryList>();
        hs.addAll(groceryList);
        groceryList.clear();
        groceryList.addAll(hs);

    for(GroceryList g : groceryList){
        Log.d(g.getName(), "onCreateView: TESTING");
    }


        //get shared pref
        SharedPreferences sharedPreferencesUid = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = sharedPreferencesUid.getString("userId" , "");

                if(groceryList.size() != 0) {
                    TextView tvHeading = (TextView) rootView.findViewById(R.id.fragment_grocery_list_heading);
                    tvHeading.setTextSize(1 , 18);

                    lv = (ListView) rootView.findViewById(R.id.list_view_grocery_list);
                    //Log.d(groceryList.get(0).getName(), "onDataChange: grocerlistActivity");
                    adapterGroceryList = new AdapterGroceryList(rootView.getContext(), groceryList);
                    //Log.d(groceryList.get(0).getName(), "onDataChange: grocerlistActivity");
                    lv.setAdapter(adapterGroceryList);
                    //Log.d(groceryList.get(0).getName(), "onDataChange: grocerlistActivity");
                    adapterGroceryList.notifyDataSetChanged();
                }
        else {

                    //ADD CODE FOR DISPLAYING NO ITEMS
                    TextView tvHeading = (TextView) rootView.findViewById(R.id.fragment_grocery_list_heading);
                    tvHeading.setText("No Items in the list");
                    tvHeading.setTextSize(1 , 18);


                }

        return rootView;
    }

}
