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

    @GET("lookup.php")
    fun getDishById(@Query("i") i: Int): Call<DishWrapper>
}

class Dish(
    val strMeal: String,
    val strArea: String,
    val idMeal: Int,
    val strMealThumb: String,

    // for mealinfosfragment
    val strInstructions: String,
    val strCategory: String,

    // ingredients
    val strIngredient1: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strIngredient10: String,
    val strIngredient11: String,
    val strIngredient12: String,
    val strIngredient13: String,
    val strIngredient14: String,
    val strIngredient15: String,
    val strIngredient16: String,
    val strIngredient17: String,
    val strIngredient18: String,
    val strIngredient19: String,
    val strIngredient20: String
    )

class DishWrapper(val meals: List<Dish>)