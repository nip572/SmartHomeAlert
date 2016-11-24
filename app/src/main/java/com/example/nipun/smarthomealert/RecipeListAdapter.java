package com.example.nipun.smarthomealert;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.View.inflate;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.example.nipun.smarthomealert.R.id.imageView;

/**
 * Created by Nipun on 11/1/16.
 */

public class RecipeListAdapter extends BaseAdapter {
    private Context mContext;
    private List<RecipeResponse> mRecipeList;

    public RecipeListAdapter(Context mContext , List<RecipeResponse> mRecipeList) {
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.recipe_item, null);
        TextView title = (TextView) v.findViewById(R.id.recipe_title);
        ImageView recpiePhoto = (ImageView) v.findViewById(R.id.imageView_recipe_photo);
        recpiePhoto.setColorFilter(Color.rgb(175, 175, 175), android.graphics.PorterDuff.Mode.MULTIPLY);
        String imageViewRecipe = mRecipeList.get(position).getImage();
        title.setText(mRecipeList.get(position).getTitle());
        Picasso.with(mContext)
                .load(imageViewRecipe)
                .into(recpiePhoto);

        return v;
    }
}
