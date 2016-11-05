package com.example.nipun.smarthomealert;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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





    }
}
