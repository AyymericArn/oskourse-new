package com.hetic.oskourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hetic.oskourse.fragments.MainFragment
import com.hetic.oskourse.fragments.MyListFragment
import com.hetic.oskourse.fragments.MyMealsFragment
import kotlinx.android.synthetic.main.toolbar.*
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import com.hetic.oskourse.dialogs.IngredientsDialog
import kotlinx.android.synthetic.main.dialog_ingredient.*

class HomeActivity : AppCompatActivity(), IngredientsDialog.NoticeDialogListener {

    val mainFrag = MainFragment()
    val mealFrag = MyMealsFragment()
    val listFrag = MyListFragment()

    var active: Fragment = mainFrag
    val fm = supportFragmentManager

    var firebaseAuth: FirebaseAuth? = null
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth?.getCurrentUser()

        database = FirebaseDatabase.getInstance()
        val dbReference: DatabaseReference = database.getReference("message")


        if (user != null) {
            dbReference.setValue("test")
        }

        toolbarName.setText(user?.displayName)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.getMenu().findItem(R.id.navigation_main).setChecked(true)

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
            .addToBackStack("one")
            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.sign_out -> {
                firebaseAuth?.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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

    fun showDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = IngredientsDialog()
        dialog.show(supportFragmentManager, "IngredientDialog")
    }

    override fun onDialogPositiveClick(toAdd: String) {

        Toast.makeText(this, toAdd, Toast.LENGTH_LONG).show()

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)

        val ingredientsString = sharedPreference.getString("ingredients", "no ingredients")
        val ingredientsList = ingredientsString.split(",").toMutableList()

        ingredientsList.add(toAdd)
        sharedPreference.edit {
            putString("ingredients", ingredientsList.toString().replace("[", "").replace("]", ""))
        }

        var frg: Fragment? = null
        frg = supportFragmentManager.findFragmentByTag("2")
        val ft = supportFragmentManager.beginTransaction()
        ft.detach(frg!!)
        ft.attach(frg)
        ft.commit()
    }

}
