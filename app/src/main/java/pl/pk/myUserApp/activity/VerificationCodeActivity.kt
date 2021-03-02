package pl.pk.myUserApp.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pl.pk.myUserApp.MainViewModel
import pl.pk.myUserApp.MainViewModelFactory
import pl.pk.myUserApp.R
import pl.pk.myUserApp.data.LoginBody
import pl.pk.myUserApp.repo.Repository

class VerificationCodeActivity : AppCompatActivity() {

    private lateinit var loginBody: LoginBody
    private lateinit var loginButton: Button
    private lateinit var totpField: EditText
    private var repository: Repository = Repository()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secret_code)
        loginBody = intent.getSerializableExtra("loginBody") as LoginBody
        loginButton = findViewById(R.id.loginButtonWithCode)
        totpField = findViewById(R.id.totpField)
        setListener()

    }

    private fun setListener() {
        loginButton.setOnClickListener {
            if (totpField.text != null) {
                var token: String
                loginBody.code = totpField.text.toString()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                viewModel.loginPost(loginBody)
                viewModel.myLoginResponse.observe(this, Observer { response ->
                    if (response.isSuccessful) {
                        token = response.body()?.access_token!!
                        var intent = Intent(baseContext, MyProfileActivity::class.java)
                        intent.putExtra("token", token)
                        startActivity(intent)
                    }else
                    {
                        AlertDialog.Builder(this)
                            .setTitle("Zły kod totp")
                            .setMessage("Podano niepoprawny kod Totp")
                            .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }
                            )
                            .show()
                    }
                })
            }
        }
    }
}
