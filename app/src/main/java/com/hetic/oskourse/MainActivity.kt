package com.hetic.oskourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser
import com.hetic.oskourse.fragments.LandingFragment


class MainActivity : AppCompatActivity() {


    var firebaseAuth: FirebaseAuth? = null

    val landingFrag = LandingFragment()

    val fm = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()

        fm.beginTransaction()
            .add(R.id.fragmentContainer, landingFrag, "1")
            .addToBackStack("landing")
            .commit()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth?.currentUser
        if (currentUser != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


    }
}
