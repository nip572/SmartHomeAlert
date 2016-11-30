package com.example.nipun.smarthomealert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewIngredientsActivity extends AppCompatActivity {
    AdapterIngredientsList adapterIngredientsList;
    GroceryList groceryList;
    List<ExtendedIngredient> extendedIngredients;
    ListView lvIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ingredients);

        //GET INTENT
        extendedIngredients = (ArrayList)getIntent().getSerializableExtra("ingredients");




        //Set Adapter
          lvIngredient = (ListView) findViewById(R.id.list_view_ingredient_master);
        adapterIngredientsList = new AdapterIngredientsList(getApplicationContext() , extendedIngredients);
        lvIngredient.setAdapter(adapterIngredientsList);
        adapterIngredientsList.notifyDataSetChanged();





    }
}
