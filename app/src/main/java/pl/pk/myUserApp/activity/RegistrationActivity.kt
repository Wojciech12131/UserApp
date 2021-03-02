package pl.pk.myUserApp.activity

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
import pl.pk.myUserApp.data.RegistrationBody
import pl.pk.myUserApp.repo.Repository

class RegistrationActivity : AppCompatActivity() {

    private lateinit var usernameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var registrationButton: Button
    private var repository: Repository = Repository()
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        setUpField()
    }

    private fun setUpField() {
        usernameField = findViewById(R.id.usernameField)
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        confirmPasswordField = findViewById(R.id.confirmPasswordField)
        registrationButton = findViewById(R.id.registrationButton)
        registrationButton.setOnClickListener {
            if (validateField()) {

                performRegistration()
            }
        }
    }

    private fun performRegistration() {
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.registrationPost(
            RegistrationBody(
                emailField.text.toString(),
                usernameField.text.toString(),
                passwordField.text.toString()
            )
        )
        viewModel.registrationResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                AlertDialog.Builder(this)
                    .setTitle("Zakończono powodzeniem")
                    .setMessage("Rejestracja się udała")
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    .show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Niepowodzenie")
                    .setMessage("Rejestracja zakończona niepowodzeniem z powodu " + response.errorBody().toString())
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        })
    }

    private fun validateField(): Boolean {
        if (usernameField.text == null || usernameField.text.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Proszę uzupełnić nazwę użytkownika")
                .setMessage("Pole nazwa użytkownika nie może być puste")
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ -> dialog.dismiss() }
                .show()
            return false
        }
        if (emailField.text == null || emailField.text.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Proszę uzupełnić email")
                .setMessage("Pole email nie może być puste")
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ -> dialog.dismiss() }
                .show()
            return false
        }
        if (passwordField.text == null || passwordField.text.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Proszę uzupełnić hasło")
                .setMessage("Pole hasło nie może być puste")
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ -> dialog.dismiss() }
                .show()
            return false
        }
        if (passwordField.text.toString() != confirmPasswordField.text.toString()) {
            AlertDialog.Builder(this)
                .setTitle("Hasła nie są zgodne")
                .setMessage("Proszę poprawnie powtórzyć hasło")
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ -> dialog.dismiss() }
                .show()
            return false
        }
        return true
    }


}