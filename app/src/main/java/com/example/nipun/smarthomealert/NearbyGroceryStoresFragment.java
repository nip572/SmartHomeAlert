package com.example.nipun.smarthomealert;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyGroceryStoresFragment extends Fragment {


    private String currentLat = "37.3353705" ;
    private  String currentLong = "-121.8849446";
    private PlaceApiModel placeApiModel;
    private RestCalls restCalls;
    private AdapterNearbyGroceryStores adapterNearbyGroceryStores;
    ListView lvNearby;
    private List<Result> result;

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    View rootView;

    public NearbyGroceryStoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_nearby_grocery_stores, container, false);

        SharedPreferences sharedPreferencesLat = getContext().getSharedPreferences("Lat", Context.MODE_PRIVATE);
        currentLat = sharedPreferencesLat.getString("currentLat" , "");
        currentLong = sharedPreferencesLat.getString("currentLong" , "");
        Log.d(currentLat + currentLong, "onCreateView: Nearby GroceryList");


        //MAKE REST CALL
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        restCalls = retrofit.create(RestCalls.class);
        final Call<PlaceApiModel> placeApiModelResponse = restCalls.getPlaceDetails(currentLat + ","+currentLong);
        placeApiModelResponse.enqueue(new Callback<PlaceApiModel>() {

            @Override
            public void onResponse(Call<PlaceApiModel> call, Response<PlaceApiModel> response) {
                int statusCode = response.code();
                placeApiModel = response.body();
                result = placeApiModel.getResults();
                Log.d(result+"", "onResponse: Title");


                lvNearby = (ListView) rootView.findViewById(R.id.list_view_nearby_fragment);
                adapterNearbyGroceryStores = new AdapterNearbyGroceryStores(rootView.getContext() , result);
                lvNearby.setAdapter(adapterNearbyGroceryStores);
                adapterNearbyGroceryStores.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<PlaceApiModel> call, Throwable t) {

            }
        });
        //END REST CALL






        return  rootView;
    }


}
