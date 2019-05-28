package com.hetic.oskourse.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApi {
//    @GET("character")
//    fun getAllMeals(): Call<CharacterWrapper>

    @GET("random.php")
    fun getRandomDish(): Call<DishWrapper>

    @GET("search.php")
    fun searchDish(@Query("s") s: String): Call<DishWrapper>
}

class Dish(
    val strMeal: String,
    val strArea: String,
    val strMealThumb: String
)

class DishWrapper(val meals: List<Dish>)