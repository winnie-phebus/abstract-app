package com.example.abstractapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.abstractapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AUTHENTICATION TINGS
        auth = Firebase.auth

        binding.loginButton.setOnClickListener { login_user() }
    }

    private fun login_user() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        if ((email == null) || (password == null)){
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
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            Toast.makeText(baseContext, "user has logged in!", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}

