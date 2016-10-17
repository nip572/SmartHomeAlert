package com.example.nipun.smarthomealert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private String ingridientsString;
    private EditText addIngridient;
    private Button addIngredientButton;
    private Button searchButton;
    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // ADD INGRIDIENT TO LIST
        addIngredientButton = (Button) findViewById(R.id.submit_for_ingridients);
        addIngridient = (EditText) findViewById(R.id.editText_add_ingridient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(ingridientsString == null){
                        ingridientsString = addIngridient.getText() + "%2C";
                        addIngridient.getText().clear();
                    }
                    else{
                        ingridientsString = ingridientsString + addIngridient.getText().toString() +"%2C";
                        addIngridient.getText().clear();
                    }
                    Log.d(ingridientsString , "onClick: ");



            }
        });

        //POST

        searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recipeCalls = retrofit.create(RecipeCalls.class);
                Call<List<RecipeResponse>> recipeResponse = recipeCalls.getRecipe(ingridientsString);
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
                }); // END CALLBACK

            }
        });


    }
}
