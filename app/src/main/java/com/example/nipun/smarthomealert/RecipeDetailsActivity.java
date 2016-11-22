package com.example.nipun.smarthomealert;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeDetailsActivity extends AppCompatActivity {

    String recipeId;
    String imageUrl;
    String recipeTitle;
    RecipeCalls recipeCalls;
    TextView tvInstructions;

    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Log.d("Having fun", "onCreate: ");
        recipeId = getIntent().getStringExtra("recipeId");
        imageUrl = getIntent().getStringExtra("imageUrl");
        recipeTitle = getIntent().getStringExtra("recipeTitle");

        //Set Image View
        ImageView imgRecipeImage = (ImageView) findViewById(R.id.recipe_details_activity_image);
        Picasso.with(this).load(imageUrl).into(imgRecipeImage);
        imgRecipeImage.setColorFilter(Color.rgb(175, 175, 175), android.graphics.PorterDuff.Mode.MULTIPLY);

        //Set Title
        TextView tvRecipeTitle = (TextView) findViewById(R.id.recipe_details_activity_recipeTitle);
        tvRecipeTitle.setText(recipeTitle);

        //make REST CALLs
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        recipeCalls = retrofit.create(RecipeCalls.class);
        final Call<RecipeDetailsResponse> recipeDetailsResponse = recipeCalls.getRecipeDetails(recipeId);
        recipeDetailsResponse.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                int statusCode = response.code();
                RecipeDetailsResponse details = response.body();
                //Log.d("recipe details activity" , String.valueOf(statusCode));
                Log.d(details.getInstructions(), "onResponse: recipeDetailsActivity");
                tvInstructions = (TextView) findViewById(R.id.recipe_details_activity_instructions);
                tvInstructions.setText(details.getInstructions());










            }



            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {

            }
        }); // END CALLBACK


    }
}
