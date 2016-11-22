package com.example.nipun.smarthomealert;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.nipun.smarthomealert.R.id.textView;

public class RecipeListActivity extends AppCompatActivity{

    private List<RecipeResponse> mRecipeList ;
    private ListView lvRecipeList;
    private RecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecipeList = new ArrayList<>();



        lvRecipeList = (ListView) findViewById(R.id.recipe_list_view);
        mRecipeList = (ArrayList)getIntent().getSerializableExtra("RecipeList");
        Log.d("TITLE" , mRecipeList.get(1).getTitle());

        adapter = new RecipeListAdapter(getApplicationContext(), mRecipeList);
        lvRecipeList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //make listView Clickable

        lvRecipeList.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Integer i = position;
                        Integer recipeIdInteger = mRecipeList.get(position).getId();

                        //Start Intent
                        Intent startRecipeDetailActivity = new Intent(RecipeListActivity.this , RecipeDetailsActivity.class);

                        //Preparing Intent
                        String recipeId = mRecipeList.get(position).getId().toString();
                        String  imageUrl = mRecipeList.get(position).getImage();
                        String  recipeTitle = mRecipeList.get(position).getTitle();

                        Log.d(recipeId, "onItemClick: imageID ");
                        Log.d(imageUrl, "onItemClick: url");
                        Log.d(recipeTitle, "onItemClick: recipeTitle");

                        //Send intent
                        startRecipeDetailActivity.putExtra("recipeId" , recipeId);
                        startRecipeDetailActivity.putExtra("imageUrl" , imageUrl);
                        startRecipeDetailActivity.putExtra("recipeTitle" , recipeTitle);

                        //Start Details Activity
                        RecipeListActivity.this.startActivity(startRecipeDetailActivity);




                    }
                }
        );






    }
}
