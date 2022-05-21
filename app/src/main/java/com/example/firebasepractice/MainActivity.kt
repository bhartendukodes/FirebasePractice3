package com.example.firebasepractice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasepractice.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var firebaseAuth:FirebaseAuth?=null
    private var progressDialog:ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        checkUserLogedInOrNot()
        onClick()
    }

    private fun checkUserLogedInOrNot() {

        val user = firebaseAuth?.currentUser
        if (user!=null)
        {
            startActivity(Intent(this, SecondActivity::class.java))
            finishAffinity()
        }
    }

    private fun onClick() {
        binding.apply {
            btnLogin.setOnClickListener {
                validate()
            }
        }
    }


    private fun validate()
    {
        binding.apply {
            var email = edEmail.text.toString()
            var password = edPassword.text.toString()
            progressDialog?.setMessage("loading")
            if (email.trim().isEmpty() || password.trim().isEmpty())
            {
                Toast.makeText(this@MainActivity, "Please fill the all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        progressDialog?.dismiss()
                        startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                        finishAffinity()
                    }
                    else{
                        progressDialog?.dismiss()
                        Toast.makeText(this@MainActivity,"Error", Toast.LENGTH_SHORT).show()
                    }
                }
                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                finishAffinity()
            }
        }
    }
}