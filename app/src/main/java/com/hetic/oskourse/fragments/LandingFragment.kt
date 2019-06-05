package com.hetic.oskourse.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hetic.oskourse.HomeActivity

import com.hetic.oskourse.R
import kotlinx.android.synthetic.main.fragment_landing.*


class LandingFragment : Fragment() {

    var firebaseAuth: FirebaseAuth? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()

        super.onViewCreated(view, savedInstanceState)

        homeButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }

        val signupFrag = SignupFragment()

        signupButton.setOnClickListener {
            getActivity()?.getSupportFragmentManager()?.beginTransaction()
                ?.replace(R.id.fragmentContainer, signupFrag, "findThisFragment")
                ?.addToBackStack("two")
                ?.commit()
        }

        signinButton.setOnClickListener {
            onUserTryLogin()
        }
    }

    fun onUserTryLogin(){
        val email = signinEmail.text.toString()
        val password = signinPassword.text.toString()

        if (email.isBlank()){
            Toast.makeText(context, "Indiquez votre mail !", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isBlank()){
            Toast.makeText(context, "Indiquez votre mot de passe !", Toast.LENGTH_SHORT).show()
            return
        }

        if (email.isNotBlank() && password.isNotBlank()){
            firebaseAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { result ->

                    if (result.isSuccessful) {

                        //Toast.makeText(context, "SUCCESS $email $password", Toast.LENGTH_SHORT).show()

                        val intent = Intent(context, HomeActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(context, "ALALAL ERREUR", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }


}
