package com.example.nipun.smarthomealert;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static android.R.id.list;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Nipun on 11/23/16.
 */

public class AdapterIngredientsList extends BaseAdapter {

    Context mContext;
    List<ExtendedIngredient> extendedIngredients;
    ToggleButton tbAddtoGroceryList;
    TextView tvIngredient;
    ImageView imageIngredient;
    DatabaseReference myRef;
    String userId;
    FireBaseModel fireBaseModel;
    List<GroceryList> groceryList;

    public AdapterIngredientsList(Context mContext , List<ExtendedIngredient> extendedIngredients ) {
        this.mContext = mContext;
        this.extendedIngredients = extendedIngredients;
    }

    @Override
    public int getCount() {
        return extendedIngredients.size();
    }

    @Override
    public Object getItem(int position) {
        return extendedIngredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //SHARED PREFERANCE
        SharedPreferences sharedPreferencesUid = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId= sharedPreferencesUid.getString("userId" , "");
        groceryList = new ArrayList<GroceryList>();




        //Log.d("TEST", "getView: TEST");
        View v = View.inflate(mContext, R.layout.list_view_ingredients_row, null);
        tvIngredient = (TextView) v.findViewById(R.id.list_view_ingredients_text);
        tbAddtoGroceryList = (ToggleButton) v.findViewById(R.id.list_view_ingredient_toggle_button);

        imageIngredient = (ImageView) v.findViewById(R.id.list_view_ingredients_img) ;
        Picasso.with(mContext)
                .load(extendedIngredients.get(position).getImage()).resize(200,200)
                .into(imageIngredient);

        //title.setText(mRecipeList.get(position).getTitle());
        //Log.d(recipeDetailsResponse.getExtendedIngredients().get(position).getName(), "getView: ingredients");
        // Log.d(extendedIngredients.get(position).getName(), "getView: ");
        tvIngredient.setText(extendedIngredients.get(position).getAmount() + " " +
                extendedIngredients.get(position).getUnit()+ " " +
                extendedIngredients.get(position).getName());

        //GET REFERNCE FOR FIREBASE
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference(userId);


        //CLICK LISTENER FOR ADD TOGGLE BUTTON
        tbAddtoGroceryList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //ENABLED

                    tbAddtoGroceryList.setTextColor(Color.parseColor("#008000"));
                    GroceryList gl = new GroceryList();
                    gl.setName(extendedIngredients.get(position).getName());
                    gl.setImageURL(extendedIngredients.get(position).getImage());
                    groceryList.add(gl);
                    fireBaseModel = new FireBaseModel();
                    fireBaseModel.setGroceryList(groceryList);
                    myRef.child("groceryList").setValue(groceryList);


                } else {
                    //DISABLED
                    tbAddtoGroceryList.setTextColor(Color.parseColor("#ff0000"));
                    Integer num = 0;
                    String nameOfIngredient = extendedIngredients.get(position).getName();
                    for(int i = 0;i < groceryList.size() ; i++ ){
                        GroceryList item = groceryList.get(i);
                        if(item.getName().equals(nameOfIngredient)){
                            num = i;
                        }
                    }
                    groceryList.remove(groceryList.get(num));
                    myRef.child("groceryList").removeValue();
                    myRef.child("groceryList").setValue(groceryList);
                    //myRgref.child("groceryList").child(i.toString()).removeValue();



                }

            }
        });






        return v;
    }
}
