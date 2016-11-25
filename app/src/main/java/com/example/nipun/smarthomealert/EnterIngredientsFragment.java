package com.example.nipun.smarthomealert;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * A simple {@link Fragment} subclass.
 */
public class EnterIngredientsFragment extends Fragment{


    public EnterIngredientsFragment() {
        // Required empty public constructor
    }
    private RestCalls restCalls;
    private String ingridientsString;
    private EditText addIngridient;
    private Button addIngredientButton;
    private Button searchButton;

    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);


        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // ADD INGRIDIENT TO LIST
        addIngredientButton = (Button) rootView.findViewById(R.id.submit_for_ingridients);
        addIngridient = (EditText) rootView.findViewById(R.id.editText_add_ingridient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), addIngridient.getText() + " added" , Toast.LENGTH_LONG);

                if(ingridientsString == null){
                    ingridientsString = addIngridient.getText() + "%2C";
                    addIngridient.getText().clear();
                }
                else{
                    ingridientsString = ingridientsString + addIngridient.getText().toString() +"%2C";
                    addIngridient.getText().clear();
                }




            }
        });

        //POST

        searchButton = (Button) rootView.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                restCalls = retrofit.create(RestCalls.class);
                Call<List<RecipeResponse>> recipeResponse = restCalls.getRecipe(ingridientsString);
                recipeResponse.enqueue(new Callback<List<RecipeResponse>>() {
                    @Override
                    public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                        int statusCode = response.code();
                        List<RecipeResponse> rr = response.body();
                        Intent recipeListIntent = new Intent(getActivity() , RecipeListActivity.class);
                        recipeListIntent.putExtra("RecipeList" , (Serializable) rr);
                        EnterIngredientsFragment.this.startActivity(recipeListIntent);
                        ingridientsString =null;

                    }



                    @Override
                    public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

                    }
                }); // END CALLBACK


            }
        });
        return rootView;
    }

}
