package com.hetic.oskourse.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.hetic.oskourse.R
import com.hetic.oskourse.services.DishWrapper
import com.hetic.oskourse.services.MealRepository
import com.hetic.oskourse.viewholder.DishItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_meal_infos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealInfosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_infos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.get("id").toString().toInt()

        val repository = MealRepository()

        repository.api.getDishById(id)
            .enqueue(object : Callback<DishWrapper> {
                override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
                    val dishWrapper = response.body()
                    if (dishWrapper != null) {

                        val res = dishWrapper.meals[0]

                        mealTitleTextView.text = res.strMeal
                        mealOriginTextView.text = res.strArea
                        mealDescriptionTextView.text = res.strInstructions
                        mealCategoryTextView.text = res.strCategory

                        Picasso.get().load(res.strMealThumb).into(imageView)

                    }
                }

                override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

}
