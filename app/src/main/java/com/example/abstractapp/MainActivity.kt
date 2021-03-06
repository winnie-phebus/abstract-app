package com.example.abstractapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.abstractapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var inLoginState: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AUTHENTICATION TINGS
        auth = Firebase.auth

        binding.switchButton.setOnClickListener { switchAuthPress() } // TODO: check for missing steps
        binding.authButton.setOnClickListener { authButtonPress() }
    }

    private fun switchAuthPress() {
        if (inLoginState) {
            binding.tagline.setText(R.string.login_subtitle)
            binding.authButton.setText(R.string.login)
            binding.switchButton.setText(R.string.register_redirect)
            binding.passwordConfirmLayout.visibility = View.GONE
        } else { // this means that the Activity is in "Register" status
            binding.tagline.setText(R.string.register_subtitle)
            binding.authButton.setText(R.string.register)
            binding.switchButton.setText(R.string.login_redirect)
            binding.passwordConfirmLayout.visibility = View.VISIBLE
        }
        print("ABSTRACT IS IN: $inLoginState")
        inLoginState = !(inLoginState)
        print("ABSTRACT IS IN: $inLoginState")
    }

    private fun authButtonPress() {
        print("ABSTRACT IS IN: $inLoginState")
        if (!inLoginState) { //TODO: investigate this, why does it work as ! not while true?? Maybe make an Enum
            loginUser()
        } else {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        val confirmpassword = binding.passwordConfirm.text.toString()

        //
        if ((password != confirmpassword)) {
            print("Error")  // TODO: sit down and make a proper error display pipeline
            errorDisplay("Confirm password does not match password. Please try again.")
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
                        errorDisplay("Authentication failed. New User Unsuccessful.")
                        updateUI(null)
                    }
                }
        }
    }

    private fun loginUser() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        if ((email == null) || (password == null)) {
            errorDisplay("Email or password empty. Please try again.")
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
                        errorDisplay("Authentication failed. Login Unsuccessful.")
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(baseContext, "user has logged in!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

    private fun errorDisplay(errText: String) {
        Toast.makeText(baseContext, errText, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

