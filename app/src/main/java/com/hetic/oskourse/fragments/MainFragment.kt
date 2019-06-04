package com.hetic.oskourse.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SharedMemory
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hetic.oskourse.HomeActivity
import android.preference.PreferenceManager
import androidx.core.content.edit

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

class MainFragment : Fragment(), TextWatcher {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val thisContext = context

        recyclerView.layoutManager = LinearLayoutManager(
            thisContext,
            RecyclerView.VERTICAL,
            false
        )

        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val fastAdapter = FastAdapter.with<DishItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        fastAdapter.withOnClickListener { view, adapter, item, position ->

            val bundle = Bundle()
            bundle.putInt("id", item.dish.idMeal)
            bundle.putBoolean("erase", false)
            bundle.putInt("position", position)
//            Toast.makeText(thisContext, "${item.dish.idMeal}", Toast.LENGTH_SHORT).show()

            val module = MealInfosFragment()
            module.arguments = bundle

            // replace fragment with the meal infos when an item in the link is clicked

            getActivity()?.getSupportFragmentManager()?.beginTransaction()
                ?.replace(R.id.fragmentContainer, module, "mealFragment")
                ?.addToBackStack("two")
                ?.commit()

            true
        }

        recyclerView.adapter = fastAdapter

        val repository = MealRepository()

        // query to the API

        fun fetchAndDisplay() {

            val search = searchBar.text.toString()

            if (search.isBlank()) {
                for (i in 0..20) {
                    repository.api.getRandomDish()
                        .enqueue(object : Callback<DishWrapper> {
                            override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
                                val dishWrapper = response.body()
                                if (dishWrapper != null) {

                                    val res = dishWrapper.meals[0]

                                    val item = DishItem(res)

                                    itemAdapter.add(item)
                                } else {
                                    itemAdapter.removeRange(0, itemAdapter.adapterItemCount)
                                }
//                        Toast.makeText(this@HomeActivity, "success", Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(thisContext, "failure", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            } else {
                repository.api.searchDish(search)
                    .enqueue(object : Callback<DishWrapper> {
                        override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
                            val dishWrapper = response.body()
                            if (dishWrapper!!.meals != null) {
                                println(dishWrapper)

                                for(myDish in dishWrapper.meals) {
                                    val item = DishItem(myDish)
                                    itemAdapter.add(item)
                                }
                            }
//                        Toast.makeText(this@HomeActivity, "success", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
                            t.printStackTrace()
                            Toast.makeText(thisContext, "failure", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

        }

        searchBar.addTextChangedListener(object: TextWatcher{

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                itemAdapter.removeRange(0, itemAdapter.adapterItemCount)
                fetchAndDisplay()
            }
        })
        deleteButton.setOnClickListener {
            itemAdapter.removeRange(0, itemAdapter.adapterItemCount)
            // debug only
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this.context)
            sharedPreference.edit {
                remove("meals")
            }
        }

        optionsButton.setOnClickListener {
            fetchAndDisplay()
        }

    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


//        Toast.makeText(this@HomeActivity, query, Toast.LENGTH_SHORT).show()
    }
}
