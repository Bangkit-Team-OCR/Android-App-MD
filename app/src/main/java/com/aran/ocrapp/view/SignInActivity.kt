package com.aran.ocrapp.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.aran.ocrapp.R
import com.aran.ocrapp.api.ApiConfig
import com.aran.ocrapp.custom.EmailCustom
import com.aran.ocrapp.custom.PassCustom
import com.aran.ocrapp.databinding.ActivitySignInBinding
import com.aran.ocrapp.helper.SharedViewModel
import com.aran.ocrapp.helper.SignInResponse
import com.aran.ocrapp.helper.UserPreference
import com.aran.ocrapp.helper.ViewModelFactory
import com.aran.ocrapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SignInActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignInBinding
    private lateinit var mainViewModel: SharedViewModel

    private lateinit var emailCustom: EmailCustom
    private lateinit var passCustom: PassCustom
    private lateinit var btnSignIn : AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupViewModel()

        binding.btnSignIn.setOnClickListener{

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            
            signIn(email, password)
        }

        binding.btnToRegis.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

//    private fun setupViewModel() {
//        mainViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[SharedViewModel::class.java]
//
//        mainViewModel.getUser().observe(this) { user ->
//            if(user.isLogin) {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//        }
//    }

    private fun signIn(email: String, password: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object: Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                showLoading(false)
                val responseBody = response.body()
                Log.d(TAG, "onResponse: $responseBody")

                if (response.isSuccessful && responseBody?.status == "success") {
//                    mainViewModel.saveUser(UserModel(responseBody.loginResult.token, true))
                    Toast.makeText(this@SignInActivity, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                    Toast.makeText(this@SignInActivity, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure2: ${t.message}")
                Toast.makeText(this@SignInActivity, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
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

    private fun setLoginButton() {
        val email = emailCustom.text.toString()
        if (passCustom.length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            btnSignIn.isEnabled =true
        }
    }
}