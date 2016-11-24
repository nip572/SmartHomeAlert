package com.example.nipun.smarthomealert;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Nipun on 11/23/16.
 */

public class AdapterIngredientsList extends BaseAdapter {

    Context mContext;
    List<ExtendedIngredient> extendedIngredients;
    TextView tvIngredient;
    ImageView imageIngredient;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        //Log.d("TEST", "getView: TEST");
        View v = View.inflate(mContext, R.layout.list_view_ingredients_row, null);
        tvIngredient = (TextView) v.findViewById(R.id.list_view_ingredients_text);
        imageIngredient = (ImageView) v.findViewById(R.id.list_view_ingredients_img) ;
        Picasso.with(mContext)
                .load(extendedIngredients.get(position).getImage()).resize(200,200)
                .into(imageIngredient);

        //title.setText(mRecipeList.get(position).getTitle());
        //Log.d(recipeDetailsResponse.getExtendedIngredients().get(position).getName(), "getView: ingredients");
       // Log.d(extendedIngredients.get(position).getName(), "getView: ");
        tvIngredient.setText(extendedIngredients.get(position).getAmount() + " " + extendedIngredients.get(position).getUnit()+ " " +extendedIngredients.get(position).getName());
        return v;
    }
}
