package com.aran.ocrapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aran.ocrapp.R
import com.aran.ocrapp.api.ApiConfig
import com.aran.ocrapp.databinding.ActivitySignUpBinding
import com.aran.ocrapp.helper.Responses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.jar.Manifest

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().createAccount(email, password)
        client.enqueue(object: Callback<Responses> {
            override fun onResponse(
                call: Call<Responses>,
                response: Response<Responses>
            ) {
                showLoading(false)
                val responseBody = response.body()
                Log.d(TAG, "onResponse: $responseBody")
                if(response.isSuccessful && responseBody?.message == "insert new user success") {
                    Toast.makeText(this@SignUpActivity, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, SecondStepActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@SignUpActivity, SecondStepActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@SignUpActivity, getString(R.string.register_fail), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Responses>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure2: ${t.message}")
                Toast.makeText(this@SignUpActivity, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}