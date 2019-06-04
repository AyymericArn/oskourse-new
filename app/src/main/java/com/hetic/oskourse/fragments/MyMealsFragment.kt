package com.hetic.oskourse.fragments


import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.hetic.oskourse.R
import com.hetic.oskourse.services.DishWrapper
import com.hetic.oskourse.services.MealRepository
import com.hetic.oskourse.viewholder.DishItem
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMealsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.context)
        val mealString = sharedPreference.getString("meals", "no meals saved").replace("[", "").replace("]", "")
        var mealList = mealString.split(",")

        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val fastAdapter = FastAdapter.with<DishItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        recyclerView.adapter = fastAdapter

        // handle clicks on meals

        fastAdapter.withOnClickListener { view, adapter, item, position ->

            val bundle = Bundle()
            bundle.putInt("id", item.dish.idMeal)
            bundle.putBoolean("erase", true)
            bundle.putInt("position", position)
//            Toast.makeText(thisContext, "${item.dish.idMeal}", Toast.LENGTH_SHORT).show()

            val module = MealInfosFragment()
            module.arguments = bundle

            // replace fragment with the meal infos when an item in the link is clicked

            getActivity()?.getSupportFragmentManager()?.beginTransaction()
                ?.replace(R.id.fragmentContainer, module, "findThisFragment")
                ?.addToBackStack("two")
                ?.commit()

            true
        }

        // for api calls

        val repository = MealRepository()

        if (mealList.size > 0) {
            if (mealList[0] == "no meal saved" || mealList[0] == "nomealsaved") {
                mealList = mealList.drop(1)
            }
            for (item in mealList) {
                repository.api.getDishById(item.trim().toIntOrNull() ?: 52772)
                    .enqueue(object : Callback<DishWrapper> {
                        override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
                            val mealWrapper = response.body()

                            if (mealWrapper != null) {
                                val res = mealWrapper.meals[0]

                                val item = DishItem(res)
                                itemAdapter.add(item)
                            }
                        }

                        override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
                            t.printStackTrace()
                            Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

        }
    }
}
