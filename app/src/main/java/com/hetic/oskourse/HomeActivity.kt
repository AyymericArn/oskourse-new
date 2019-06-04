package com.hetic.oskourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hetic.oskourse.fragments.MainFragment
import com.hetic.oskourse.fragments.MyListFragment
import com.hetic.oskourse.fragments.MyMealsFragment

class HomeActivity : AppCompatActivity() {

    val mainFrag = MainFragment()
    val mealFrag = MyMealsFragment()
    val listFrag = MyListFragment()

    var active: Fragment = mainFrag
    val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        fm.beginTransaction()
            .add(R.id.fragmentContainer, mealFrag, "3")
            .hide(mealFrag)
            .commit()

        fm.beginTransaction()
            .add(R.id.fragmentContainer, listFrag, "2")
            .hide(listFrag)
            .commit()

        fm.beginTransaction()
            .add(R.id.fragmentContainer, mainFrag, "1")
            .commit()


//        recyclerView.layoutManager = LinearLayoutManager(
//            this,
//            RecyclerView.VERTICAL,
//            false
//        )
//
//        val itemAdapter = ItemAdapter<IItem<*, *>>()
//        val fastAdapter = FastAdapter.with<DishItem, ItemAdapter<IItem<*, *>>>(itemAdapter)
//
//        fastAdapter.withOnClickListener { view, adapter, item, position ->
////            val intent = Intent(this, MealActivity::class.java)
////            startActivity(intent)
//            true
//        }
//
//        recyclerView.adapter = fastAdapter
//
//        val repository = MealRepository()
//
//        // search bar handler
//
////        searchBar.addTextChangedListener(object: TextWatcher {
////            override fun afterTextChanged(str: Editable?) {
////                val content = str?.text.toString()
////                str?.error = if (content.length >= 6) null else "Minimum length = 6"
////            }
////
////            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
////
////            override fun onTextChanged(s: Editable?) {
////                Toast.makeText(this@HomeActivity, s, Toast.LENGTH_SHORT).show()
////            }
////        })
//
//        searchBar.addTextChangedListener(this)
//
//        // query to the API
//
//        fun fetchAndDisplay() {
//
//            val search = searchBar.text.toString()
//
//            if (search.isBlank()) {
//                Toast.makeText(this@HomeActivity, "is blank", Toast.LENGTH_SHORT).show()
//                for (i in 0..20) {
//                    repository.api.getRandomDish()
//                        .enqueue(object : Callback<DishWrapper> {
//                            override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
//                                val dishWrapper = response.body()
//                                if (dishWrapper != null) {
//
//                                    val res = dishWrapper.meals[0]
//
//                                    val item = DishItem(res)
//
//                                    itemAdapter.add(item)
//                                }
////                        Toast.makeText(this@HomeActivity, "success", Toast.LENGTH_SHORT).show()
//                            }
//
//                            override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
//                                t.printStackTrace()
//                                Toast.makeText(this@HomeActivity, "failure", Toast.LENGTH_SHORT).show()
//                            }
//                        })
//                }
//            } else {
//                repository.api.searchDish(search)
//                    .enqueue(object : Callback<DishWrapper> {
//                        override fun onResponse(call: Call<DishWrapper>, response: Response<DishWrapper>) {
//                            val dishWrapper = response.body()
//                            if (dishWrapper != null) {
//
//                                for(myDish in dishWrapper.meals) {
//                                    val item = DishItem(myDish)
//                                    itemAdapter.add(item)
//                                }
//                            }
////                        Toast.makeText(this@HomeActivity, "success", Toast.LENGTH_SHORT).show()
//                        }
//
//                        override fun onFailure(call: Call<DishWrapper>, t: Throwable) {
//                            t.printStackTrace()
//                            Toast.makeText(this@HomeActivity, "failure", Toast.LENGTH_SHORT).show()
//                        }
//                    })
//            }
//
//        }
//
//        deleteButton.setOnClickListener {
//            itemAdapter.removeRange(0, itemAdapter.adapterItemCount)
//        }
//
//        searchButton.setOnClickListener {
//            fetchAndDisplay()
//        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_meals -> {
                fm.beginTransaction().hide(this.active).show(this.mealFrag).commit()
                this.active = mealFrag
                // Toast.makeText(this, this.active.toString(), Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_main -> {
                fm.beginTransaction().hide(this.active).show(this.mainFrag).commit()
                this.active = mainFrag
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_list -> {
                fm.beginTransaction().hide(this.active).show(this.listFrag).commit()
                this.active = listFrag
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

//    override fun afterTextChanged(s: Editable?) {
//    }
//
//    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//    }
//
//    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        val query = searchBar.text.toString()
//
////        Toast.makeText(this@HomeActivity, query, Toast.LENGTH_SHORT).show()
//    }
}
