package com.example.abstractapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val ANONYMOUS: String = "ANON"
    private lateinit var auth: FirebaseAuth

    // TODO: properly credit https://firebase.google.com/codelabs/firebase-android?authuser=0#5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth and check if the user is signed in
        auth = Firebase.auth
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            //startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in.
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            //startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
    }

    private fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    private fun getUserName(): String? {
        val user = auth.currentUser
        return if (user != null) {
            user.displayName
        } else ANONYMOUS
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this)
        //startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}