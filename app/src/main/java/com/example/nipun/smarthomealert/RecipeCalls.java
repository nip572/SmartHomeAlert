package com.example.nipun.smarthomealert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Nipun on 10/8/16.
 */

public interface RecipeCalls {


    @GET("findByIngredients?fillIngredients=false&ingredients=apples%2Cflour%2Csugar/")
    @Headers("X-Mashape-Key:Wx48O1cHuGmshBNjAiZ1Jf6c1va3p1tb1tHjsnS1s6NaXsgtzo")
    Call<List<RecipeResponse>> getRecipe();


}
