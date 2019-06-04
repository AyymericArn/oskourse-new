package com.hetic.oskourse.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hetic.oskourse.R
import kotlinx.android.synthetic.main.fragment_signup.*
import com.google.firebase.auth.UserProfileChangeRequest
import com.hetic.oskourse.HomeActivity


class SignupFragment : Fragment() {

    var firebaseAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        signupValidation.setOnClickListener{
            signUp()
        }


    }

    fun signUp(){
        val email = signupEmail.text.toString()
        val password = signupPassword.text.toString()
        val confirmPassword = signupPasswordConfirm.text.toString()
        val name = signupName.text.toString()

        if (name.isBlank()){
            Toast.makeText(context, "Indiquez votre nom !", Toast.LENGTH_SHORT).show()
            return
        }


        if (email.isBlank()){
            Toast.makeText(context, "Indiquez votre adresse mail !", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isBlank()){
            Toast.makeText(context, "Indiquez votre mot de passe !", Toast.LENGTH_SHORT).show()
            return
        }

        if (confirmPassword.isBlank()){
            Toast.makeText(context, "Confirmez votre mot de passe !", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword){
            Toast.makeText(context, "Les mots de passe ne correspondent pas !", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth?.getCurrentUser()

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)

                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context, "Impossible de créer l'utilisateur, veuillez réessayer",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                // ...
            }


    }


}
