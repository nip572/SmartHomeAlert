package com.example.nipun.smarthomealert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.id;

/**
 * Created by Nipun on 10/8/16.
 */

public interface RestCalls {


    //@GET("findByIngredients?fillIngredients=false&ingredients=apples%2Cflour%2Csugar/")
    @GET("findByIngredients?fillIngredients=false")
    @Headers("X-Mashape-Key:Wx48O1cHuGmshBNjAiZ1Jf6c1va3p1tb1tHjsnS1s6NaXsgtzo")
    Call<List<RecipeResponse>> getRecipe(@Query("ingredients") String ingredients);

    //GET ACTIVITY DETAILS
    //recipes/664575/information?includeNutrition=true
    @GET("{recipeId}/information?includeNutrition=true")
    @Headers("X-Mashape-Key:Wx48O1cHuGmshBNjAiZ1Jf6c1va3p1tb1tHjsnS1s6NaXsgtzo")
    Call<RecipeDetailsResponse> getRecipeDetails(@Path("recipeId") String recipeId);

    //MAKE REST CALL TO PLACES API
    @GET("json?radius=1000&types=grocery_or_supermarket&key=AIzaSyAF8QXex5dgm0UIjBTZLd-e6EgjYUHnuw0")
    Call<PlaceApiModel> getPlaceDetails(@Query("location") String latLong);




}
