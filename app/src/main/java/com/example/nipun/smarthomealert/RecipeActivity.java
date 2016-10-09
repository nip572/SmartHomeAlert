package com.example.nipun.smarthomealert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity {

    private RecipeCalls recipeCalls;
    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

         recipeCalls = retrofit.create(RecipeCalls.class);
        Call<List<RecipeResponse>> recipeResponse = recipeCalls.getRecipe();
        recipeResponse.enqueue(new Callback<List<RecipeResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                int statusCode = response.code();
                List<RecipeResponse> rr = response.body();
                Log.d("inside On response " , String.valueOf(statusCode));
                Log.d("onResponse: " , (rr.get(1).getTitle()));

            }

            @Override
            public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

            }
        });
    }
}
