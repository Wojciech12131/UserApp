package pl.pk.myUserApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pl.pk.myUserApp.activity.MyProfileActivity
import pl.pk.myUserApp.activity.RegistrationActivity
import pl.pk.myUserApp.activity.VerificationCodeActivity
import pl.pk.myUserApp.data.LoginBody
import pl.pk.myUserApp.repo.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registrationButton: Button
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var viewModel: MainViewModel
    private var repository: Repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton = findViewById(R.id.loginButton)
        registrationButton = findViewById(R.id.registrationButton)
        usernameField = findViewById(R.id.usernameField)
        passwordField = findViewById(R.id.passwordField)
        setListener()


    }

    private fun setListener() {
        loginButton.setOnClickListener() {
            if (usernameField.text != null && passwordField.text != null) {
                val username = usernameField.text
                val password = passwordField.text
                val loginBody = LoginBody(username.toString(), password.toString(), null)
                val viewModelFactory = MainViewModelFactory(repository)
                var token: String
                viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                viewModel.loginPost(loginBody)
                viewModel.myLoginResponse.observe(this, Observer { response ->
                    if (response.isSuccessful) {
                        token = response.body()?.access_token!!
                        var intent = Intent(baseContext, MyProfileActivity::class.java)
                        intent.putExtra("token", token)
                        startActivity(intent)
                    } else {
                        print(response)
                        var responseMessage = response.errorBody()?.string()
                        if (responseMessage?.contains("Invalid verification code", true) == true) {
                            var intent = Intent(baseContext, VerificationCodeActivity::class.java)
                            intent.putExtra("loginBody", loginBody)
                            startActivity(intent)
                        }

                    }

                })

            }
        }
        registrationButton.setOnClickListener() {
            var intent = Intent(baseContext, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }


}