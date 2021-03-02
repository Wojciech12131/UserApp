package pl.pk.myUserApp.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_my_profile.*
import okhttp3.internal.wait
import pl.pk.myUserApp.MainViewModel
import pl.pk.myUserApp.MainViewModelFactory
import pl.pk.myUserApp.R
import pl.pk.myUserApp.data.AboutMe
import pl.pk.myUserApp.fragment.CardsFragment
import pl.pk.myUserApp.fragment.CouponsFragment
import pl.pk.myUserApp.fragment.ProfileFragment
import pl.pk.myUserApp.fragment.TransactionFragment
import pl.pk.myUserApp.repo.Repository
import java.lang.Thread.sleep

class MyProfileActivity : AppCompatActivity() {

    private lateinit var accessToken: String
    private lateinit var aboutMe: AboutMe
    private var repository: Repository = Repository()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        accessToken = intent.getStringExtra("token").toString()
        getAboutMe()
    }

    private fun setNavigationBarListener() {

        val firstFragment = ProfileFragment.newInstance(aboutMe, accessToken)
        val secondFragment = CardsFragment.newInstance(aboutMe)
        val transactionFragment = TransactionFragment.newInstance(accessToken, repository)
        var couponsFragment = CouponsFragment.newInstance(accessToken,repository)
        setCurrentFragment(firstFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> setCurrentFragment(firstFragment)
                R.id.cards -> setCurrentFragment(secondFragment)
                R.id.transaction -> setCurrentFragment(transactionFragment)
                R.id.coupons -> setCurrentFragment(couponsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }

    private fun getAboutMe() {

        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAboutMe(accessToken)
        viewModel.aboutMe.observe(this, Observer { response ->
            if (response.isSuccessful) {
                aboutMe = response.body()!!
                setNavigationBarListener()


            } else {
                AlertDialog.Builder(this)
                    .setTitle("Wystąpił nieoczekiwany błąd!")
                    .setMessage(
                        "Wystąpił nieoczekiwany błąd podczas ładowania profilu," +
                                "prosimy o ponowne zalogowanie, w razie dalszych problemów prosimy spróbować póżniej," +
                                "przepraszamy za problemy."
                    )
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        dialog.dismiss()
                        finish()
                    }
                    .show()
            }
        })

    }
}