package com.hetic.oskourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hetic.oskourse.services.DishWrapper
import com.hetic.oskourse.services.MealRepository
import com.hetic.oskourse.viewholder.DishItem
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.row_dish.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HomeActivity : AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val fastAdapter = FastAdapter.with<DishItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        fastAdapter.withOnClickListener { view, adapter, item, position ->
            val intent = Intent(this, MealActivity::class.java)
            startActivity(intent)
            true
        }

        recyclerView.adapter = fastAdapter

        val repository = MealRepository()

        // search bar handler

//        searchBar.addTextChangedListener(object: TextWatcher {
//            override fun afterTextChanged(str: Editable?) {
//                val content = str?.text.toString()
//                str?.error = if (content.length >= 6) null else "Minimum length = 6"
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
//
//            override fun onTextChanged(s: Editable?) {
//                Toast.makeText(this@HomeActivity, s, Toast.LENGTH_SHORT).show()
//            }
//        })

        searchBar.addTextChangedListener(this)

        // query to the API

        fun fetchAndDisplay() {

            val search = searchBar.text.toString()

            if (search.isBlank()) {
                Toast.makeText(this@HomeActivity, "is blank", Toast.LENGTH_SHORT).show()
                for (i in 0..20) {
                    repository.api.getRandomDish()
                        .enqueue(object : Callback<DishWrapper> {
                            override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
                                val dishWrapper = response.body()
                                if (dishWrapper != null) {

                                    val res = dishWrapper.meals[0]

                                    val item = DishItem(res)

                                    itemAdapter.add(item)
                                }
//                        Toast.makeText(this@HomeActivity, "success", Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(this@HomeActivity, "failure", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            } else {
                repository.api.searchDish(search)
                    .enqueue(object : Callback<DishWrapper> {
                        override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
                            val dishWrapper = response.body()
                            if (dishWrapper != null) {

                                for(myDish in dishWrapper.meals) {
                                    val item = DishItem(myDish)
                                    itemAdapter.add(item)
                                }
                            }
//                        Toast.makeText(this@HomeActivity, "success", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
                            t.printStackTrace()
                            Toast.makeText(this@HomeActivity, "failure", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

        }

        deleteButton.setOnClickListener {
            itemAdapter.removeRange(0, itemAdapter.adapterItemCount)
        }

        searchButton.setOnClickListener {
            fetchAndDisplay()
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val query = searchBar.text.toString()

//        Toast.makeText(this@HomeActivity, query, Toast.LENGTH_SHORT).show()
    }
}
