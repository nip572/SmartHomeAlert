package com.example.nipun.smarthomealert;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.util.Config.LOGD;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterIngredientsFragment extends Fragment{


    public EnterIngredientsFragment() {
        // Required empty public constructor
    }
    private RestCalls restCalls;
    private String ingridientsString = "";
    private EditText addIngridient;
    private FancyButton addIngredientButton;
    private FancyButton searchButton;
    private List<String> ingredientsList;
    private ListView ingredientsListView;
    ArrayAdapter<String> itemsAdapter;
    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/";
    String[] test2 = {"abc", "ddd" , "wbdwbd"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ingredientsList = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, ingredientsList);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view_ingredients_in_enter_ingredients_fragment_master);
        listView.setAdapter(itemsAdapter);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // ADD INGRIDIENT TO LIST
        addIngredientButton = (FancyButton) rootView.findViewById(R.id.submit_for_ingridients);
        addIngridient = (EditText) rootView.findViewById(R.id.editText_add_ingridient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getActivity(), addIngridient.getText() + "Added",
                        Toast.LENGTH_SHORT).show();
                    ingridientsString = ingridientsString+ addIngridient.getText() + ",";
                    ingredientsList.add(addIngridient.getText().toString());
                    itemsAdapter.notifyDataSetChanged();
                    addIngridient.getText().clear();
            }
        });

        //POST

        searchButton = (FancyButton) rootView.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getActivity(), "Searching",
                        Toast.LENGTH_SHORT).show();
                restCalls = retrofit.create(RestCalls.class);
                String s = ingridientsString;
                Call<List<RecipeResponse>> recipeResponse = restCalls.getRecipe(s);
                recipeResponse.enqueue(new Callback<List<RecipeResponse>>() {
                    @Override
                    public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                        int statusCode = response.code();
                        Log.d(ingridientsString, "onResponse: ingredientList");
                        List<RecipeResponse> rr = response.body();
                        Intent recipeListIntent = new Intent(getActivity() , RecipeListActivity.class);
                        recipeListIntent.putExtra("RecipeList" , (Serializable) rr);
                        EnterIngredientsFragment.this.startActivity(recipeListIntent);
                        ingridientsString =null;
                    }

                    @Override
                    public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

                        Toast.makeText(getActivity(), "Not Found",
                                Toast.LENGTH_SHORT).show();
                    }
                }); // END CALLBACK


            }
        });
        return rootView;
    }

}