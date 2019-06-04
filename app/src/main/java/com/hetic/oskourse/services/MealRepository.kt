package com.hetic.oskourse.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MealRepository {

    val api: MealApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MealApi::class.java)
    }
}