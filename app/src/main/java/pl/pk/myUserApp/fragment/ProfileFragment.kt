package pl.pk.myUserApp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.fragment_profile.*
import pl.pk.myUserApp.R
import pl.pk.myUserApp.activity.MyProfileActivity
import pl.pk.myUserApp.data.AboutMe

private var ARG_ABOUT_ME = "aboutMe"
private var ARG_TOKEN = "token"

class ProfileFragment : Fragment() {
    private lateinit var aboutMe: AboutMe
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            aboutMe = it.getSerializable(ARG_ABOUT_ME) as AboutMe
            token = it.getString(ARG_TOKEN) as String
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        userEmail.text = aboutMe.email
        username.text = aboutMe.username
        pointsNumber.text = aboutMe.points.points.toString() + " punktów"
        if (aboutMe.using2FA) {
            using2FA.text = "Włączone"
        } else {
            using2FA.text = "Wyłączone"
        }
        cardsNumber.text = aboutMe.cards.idnNo[0]

        settingsButton.setOnClickListener { v ->
            val popupMenu = PopupMenu(context, v)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.changePersonalData -> Log.d("menu", "Zmiana danych")
                    R.id.changePassword -> Log.d("menu", "Zmiana hasła")
                    R.id.twoFactorAuthorizeSettings -> Log.d("menu", "zmiana ustawień 2fa")
                }
                true
            }
            popupMenu.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(aboutMe: AboutMe, token: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ABOUT_ME, aboutMe)
                    putString(ARG_TOKEN, token)
                }
            }
    }
}



