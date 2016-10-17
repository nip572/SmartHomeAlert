package com.example.nipun.smarthomealert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nipun on 10/8/16.
 */

public interface RecipeCalls {


    //@GET("findByIngredients?fillIngredients=false&ingredients=apples%2Cflour%2Csugar/")
    @GET("findByIngredients?fillIngredients=false")
    @Headers("X-Mashape-Key:Wx48O1cHuGmshBNjAiZ1Jf6c1va3p1tb1tHjsnS1s6NaXsgtzo")
    Call<List<RecipeResponse>> getRecipe(@Query("ingredients") String ingredients);


}
