package com.hetic.oskourse.fragments


import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.hetic.oskourse.R
import com.hetic.oskourse.services.Dish
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
        val id: Int = arguments?.get("id").toString().toInt()
        val erase: Boolean = arguments?.get("erase").toString().toBoolean()
        val mealPosition: Int = arguments?.get("position").toString().toInt()

        val repository = MealRepository()

        var ingredients = listOf<String>()

        if (!erase) {
            addMealButton.setOnClickListener{

                val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.context)

                // gets the old list of ingredients (local)

                var ingredientString = sharedPreference.getString("ingredients", "no ingredients")

                val ingredientList = ingredientString.split(",")

                val newIngredients = arrayListOf<String>()

                newIngredients.addAll(ingredients)
                ingredientList.forEach {
                    if (!ingredients.contains(it.trim())) {
                        newIngredients.add(it)
                    }
                }

                // get the old list of registered meals

                val mealsString = sharedPreference.getString("meals", "no meal saved")

                var mealList = mealsString.split(",").toMutableList()

//            if (!mealList.contains(id.toString().trim())) {
//                Toast.makeText(context, id.toString(), Toast.LENGTH_LONG).show()
//                mealList.add(id.toString().trim())
//            }

                // checks if meal is already in the list
                var existingMeal: Boolean = false
                mealList.forEach {
                    if (it.trim() == id.toString().trim()) {
                        existingMeal = true
                    }
                }
                if (!existingMeal) {
                    mealList.add(id.toString().trim())
                }

                sharedPreference.edit {
                    putString("ingredients", newIngredients.toString().trim('[').trim(']').replace("\\s".toRegex(), ""))
                    putString("meals", mealList.toString().trim('[').trim(']').trim().replace("\\s".toRegex(), ""))
                }

                activity?.onBackPressed()
            }
        } else {
            addMealButton.text = "Remove from my list"

            addMealButton.setOnClickListener{

                val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.context)

                // gets the old list of ingredients (local)

                var ingredientString = sharedPreference.getString("ingredients", "no ingredients")

                val ingredientList = ingredientString.split(",").toMutableList()

                ingredients.forEach {
                    ingredientList.remove(it.replace("\\s".toRegex(), ""))
                }

                // get the old list of registered meals

                val mealsString = sharedPreference.getString("meals", "no meal saved")

                var mealList = mealsString.split(",").toMutableList()


                // supress meal from my meals
                mealList.removeAt(mealPosition)

                sharedPreference.edit {
                    putString("ingredients", ingredientList.toString().trim('[').trim(']').replace("\\s".toRegex(), ""))
                    putString("meals", mealList.toString().trim('[').trim(']').trim().replace("\\s".toRegex(), ""))
                }

                activity?.onBackPressed()
            }
        }

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

                        // dynamise it

                        var roughIngredientsList = mutableListOf(
                            res.strIngredient1,
                            res.strIngredient2,
                            res.strIngredient3,
                            res.strIngredient4,
                            res.strIngredient5,
                            res.strIngredient6,
                            res.strIngredient7,
                            res.strIngredient8,
                            res.strIngredient9,
                            res.strIngredient10,
                            res.strIngredient11,
                            res.strIngredient12,
                            res.strIngredient13,
                            res.strIngredient14,
                            res.strIngredient15,
                            res.strIngredient16,
                            res.strIngredient17,
                            res.strIngredient18,
                            res.strIngredient19,
                            res.strIngredient20
                        )

                        ingredients = roughIngredientsList.filter { value ->
                            value.isNotEmpty()
                        }


                    }
                }

                override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

}
