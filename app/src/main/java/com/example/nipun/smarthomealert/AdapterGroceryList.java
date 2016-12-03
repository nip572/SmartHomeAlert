package com.example.nipun.smarthomealert;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nipun on 12/1/16.
 */

public class AdapterGroceryList extends BaseAdapter{


    Context mContext;
    TextView tvIngredient;
    ImageView imageIngredient;
    DatabaseReference myRef;
    String userId;
    FireBaseModel fireBaseModel;
    List<GroceryList> groceryList;


    public AdapterGroceryList(Context mContext , List<GroceryList> groceryList ) {
        this.mContext = mContext;
        this.groceryList = groceryList;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //SHARED PREFERANCE
        SharedPreferences sharedPreferencesUid = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId= sharedPreferencesUid.getString("userId" , "");
        groceryList = new ArrayList<GroceryList>();

        View v = View.inflate(mContext, R.layout.list_view_grocerylist_row, null);
        tvIngredient = (TextView) v.findViewById(R.id.list_view_grocery_list_text);
        imageIngredient = (ImageView) v.findViewById(R.id.list_view_grocery_list_img) ;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference(userId);
        tvIngredient.setText(groceryList.get(position).getName());
        Picasso.with(mContext)
                .load(groceryList.get(position).getImageURL()).resize(200,200)
                .into(imageIngredient);
        return null;
    }
}
