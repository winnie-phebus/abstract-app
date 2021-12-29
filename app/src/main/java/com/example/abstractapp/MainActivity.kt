package com.example.abstractapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.abstractapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AUTHENTICATION TINGS
        auth = Firebase.auth

        binding.registerButton.setOnClickListener { registerPress() } // TODO: check for missing steps
        binding.loginButton.setOnClickListener { loginUser() }
    }

    private fun registerPress() {
        binding.loginUsernameLayout.visibility = View.GONE
        binding.loginPasswordLayout.visibility = View.GONE
        binding.fragmentContainerView.visibility = View.VISIBLE
    }

    private fun registerUser() {
        val email = "test" // TODO: finalize registration UI and make this functional
        val password = "test" // TODO: RE-UI, maybe consider a confirmation Dialog??

        if ((email == null) || (password == null)) {
            print("Error")
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun loginUser() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        if ((email == null) || (password == null)) {
            print("Error!")
            // TODO: add an error message to user here stating what went wrong,
            // I probably can make an error() method that can also display login errors as needed
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(baseContext, "user has logged in!", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}

