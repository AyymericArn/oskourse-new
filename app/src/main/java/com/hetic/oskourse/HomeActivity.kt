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

    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_meals -> {
                fm.beginTransaction().hide(this.active).show(this.mealFrag).commit()
                this.active = mealFrag

                val mealsFragment = fm.findFragmentByTag("mealFragment")
                if (mealsFragment != null && mealsFragment.isVisible()) {
                    this.onBackPressed()
                }

                // Toast.makeText(this, this.active.toString(), Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_main -> {
                fm.beginTransaction().hide(this.active).show(this.mainFrag).commit()
                this.active = mainFrag
                val mealsFragment = fm.findFragmentByTag("mealFragment")
                if (mealsFragment != null && mealsFragment.isVisible()) {
                    this.onBackPressed()
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_list -> {
                fm.beginTransaction().hide(this.active).show(this.listFrag).commit()
                this.active = listFrag
                val mealsFragment = fm.findFragmentByTag("mealFragment")
                if (mealsFragment != null && mealsFragment.isVisible()) {
                    this.onBackPressed()
                }

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
