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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static android.R.id.list;
import static android.R.id.toggle;
import static android.media.CamcorderProfile.get;
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
    static List<GroceryList> groceryList;
    List<GroceryList> gl;
    boolean forAdd;
    View v;


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





        //Log.d("TEST", "getView: TEST");
        v = View.inflate(mContext, R.layout.list_view_ingredients_row, null);
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

        DatabaseReference firebasetRef = database.getInstance().getReference(userId);
        firebasetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                FireBaseModel fireBaseModel = dataSnapshot.getValue(FireBaseModel.class);
                 gl = fireBaseModel.getGroceryList();
                Log.d(TAG, "onDataChange: ");
/*

                if(gl != null) {
                    tbAddtoGroceryList.setOnCheckedChangeListener(null);
                    for(GroceryList g : groceryList){
                        for(ExtendedIngredient eI : extendedIngredients){
                            if(g.getName().equals(eI.getName())){
                                tbAddtoGroceryList.setChecked(true);
                            }
                            else {
                                tbAddtoGroceryList.setChecked(false);
                            }

                        }
                    }

                }
*/


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", "");
            }
        });


        //CLICK LISTENER FOR ADD TOGGLE BUTTON
        tbAddtoGroceryList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //ENABLED
                    Log.d(TAG, "onCheckedChanged: I AM BEING CALLED");
                    tbAddtoGroceryList.setTextColor(Color.parseColor("#008000"));
                    GroceryList groceryItem = new GroceryList();
                    groceryItem.setName(extendedIngredients.get(position).getName());
                    groceryItem.setImageURL(extendedIngredients.get(position).getImage());
                            Log.d("Existing List",""+gl);
                            if(!gl.contains(groceryItem)){
                                gl.add(groceryItem);
                                fireBaseModel = new FireBaseModel();
                                fireBaseModel.setGroceryList(gl);
                                myRef.child("groceryList").setValue(gl);
                            }else{
                                Context context = v.getContext();
                                CharSequence text = "Item Already Added in Shopping List!";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

                    }



                 else {
                    //DISABLED
                    tbAddtoGroceryList.setTextColor(Color.parseColor("#ff0000"));
                    Integer num = 0;
                    String nameOfIngredient = extendedIngredients.get(position).getName();

                    Log.d("RemoveList",""+extendedIngredients.get(position).getName());
                    Log.d("gl",""+gl);
                    GroceryList removeItem = new GroceryList();
                    removeItem.setName(extendedIngredients.get(position).getName());
                   if(gl.contains(removeItem)){
                       gl.remove(removeItem);
                       myRef.child("groceryList").setValue(null);
                       myRef.child("groceryList").setValue(gl);

                   }



                }

            }
        });
        return v;
    }
}
