package com.example.nipun.smarthomealert;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
    String likes;
    RestCalls restCalls;
    TextView tvInstructions;
    String steps;
    TextView tvReadyInMin;
    TextView tvLikes;
    TextView tvNoOfIngredients;
    TextView tvCalories;
    TextView tvServings;
    TextView tvProtein;
    TextView tvCarbs;
    TextView tvFats;
    TextView tvFiber;
    ListView lvIngredient;
    Button buttonViewIngredients;
    Button buttonViewMethod;
    List<ExtendedIngredient> ingredients;
    AdapterIngredientsList adapterIngredientsList;
    //List<ExtendedIngredient> extendedIngredients;



    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get Intent
        recipeId = getIntent().getStringExtra("recipeId");
        imageUrl = getIntent().getStringExtra("imageUrl");
        recipeTitle = getIntent().getStringExtra("recipeTitle");
        likes = getIntent().getStringExtra("likes");

        //Set Image View
        ImageView imgRecipeImage = (ImageView) findViewById(R.id.recipe_details_activity_image);
        Picasso.with(this).load(imageUrl).into(imgRecipeImage);
        imgRecipeImage.setColorFilter(Color.rgb(175, 175, 175), android.graphics.PorterDuff.Mode.MULTIPLY);

        //Set Title
        final TextView tvRecipeTitle = (TextView) findViewById(R.id.recipe_details_activity_recipeTitle);
        tvRecipeTitle.setText(recipeTitle);

        //make REST CALLs
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restCalls = retrofit.create(RestCalls.class);
        final Call<RecipeDetailsResponse> recipeDetailsResponse = restCalls.getRecipeDetails(recipeId);
        recipeDetailsResponse.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                int statusCode = response.code();
                RecipeDetailsResponse details = response.body();
                //Log.d("recipe details activity" , String.valueOf(statusCode));
              //  Log.d(details.getInstructions(), "onResponse: recipeDetailsActivity");
               // tvInstructions = (TextView) findViewById(R.id.recipe_details_activity_instructions);
                String s1 = "<li>";
                String s2 = "</li>";
                steps = details.getInstructions().replaceAll("\\<[^>]*>","");
                String[] stepsBreakdown = steps.split("[0-9]+\\.");
                String s3 = "";
                for(String s : stepsBreakdown){
                    s3 = s + "\r\n";
                }
               // Log.d(s3, "onResponse: newline ");
              //  tvInstructions.setText(s3);

                //Set ready In Minutes
                tvReadyInMin = (TextView) findViewById(R.id.recipe_details_activity_time);
                tvReadyInMin.setText(details.getReadyInMinutes() + " Min");

                //set Total No. Of Ingredients
                tvNoOfIngredients = (TextView) findViewById(R.id.recipe_details_activity_no_of_ingredients);
             ingredients = details.getExtendedIngredients();
                Integer noOfigredients =ingredients.size();
                tvNoOfIngredients.setText(noOfigredients.toString() + " Ingr       |");

                //Set Calories
                tvCalories = (TextView) findViewById(R.id.recipe_details_activity_calories);
                String calories = details.getNutrition().getNutrients().get(0).getAmount().toString();
                tvCalories.setText(calories + " Cal     |");

                //Set Servings
                tvServings = (TextView) findViewById(R.id.recipe_details_activity_servings);
                tvServings.setText(details.getServings().toString() + " Servings");

                //Set Adapter


                //Set Macros
                tvProtein = (TextView) findViewById(R.id.recipe_details_activity_protein);
                tvProtein.setText(details.getNutrition().getNutrients().get(7).getAmount().toString() + " g");
                tvCarbs = (TextView) findViewById(R.id.recipe_details_activity_carbs);
                tvCarbs.setText(details.getNutrition().getNutrients().get(3).getAmount().toString() + " g");
                tvFats = (TextView) findViewById(R.id.recipe_details_activity_fats);
                tvFats.setText(details.getNutrition().getNutrients().get(1).getAmount().toString() + " g");
                tvFiber = (TextView) findViewById(R.id.recipe_details_activity_fiber);
                tvFiber.setText(details.getNutrition().getNutrients().get(11).getAmount().toString() + " g");
            }
            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {

            }
        }); // END CALLBACK

            buttonViewIngredients = (Button) findViewById(R.id.recipe_details_activity_view_ingredients_button);
        buttonViewIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SEND INGREDIENTS
               Intent ingredientsIntent = new Intent(RecipeDetailsActivity.this , ViewIngredientsActivity.class);

//                Log.d(extendedIngredients.get(0).getName(), "onClick: jbtgijjbbt ij");
                ingredientsIntent.putExtra("ingredients" , (Serializable) ingredients);
                RecipeDetailsActivity.this.startActivity(ingredientsIntent);
            }
        });

    }
}
