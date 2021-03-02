package pl.pk.myUserApp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.pk.myUserApp.data.*
import pl.pk.myUserApp.repo.Repository
import retrofit2.Response
import java.util.*

class MainViewModel(private val repository: Repository) : ViewModel() {
    val myLoginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    val registrationResponse: MutableLiveData<Response<Objects>> = MutableLiveData()
    val aboutMe: MutableLiveData<Response<AboutMe>> = MutableLiveData()
    val transactions: MutableLiveData<Response<List<Transactions>>> = MutableLiveData()
    var coupons: MutableLiveData<Response<List<CouponDetails>>> = MutableLiveData()

    fun loginPost(loginBody: LoginBody) {
        viewModelScope.launch {
            var response = repository.loginPost(loginBody)
            myLoginResponse.value = response
        }
    }

    fun registrationPost(registrationBody: RegistrationBody) {
        viewModelScope.launch {
            var response = repository.registrationPost(registrationBody)
            registrationResponse.value = response
        }
    }

    fun getAboutMe(token: String) {
        viewModelScope.launch {
            var response = repository.getAboutMe(token)
            aboutMe.value = response
        }
    }

    fun getMyTransactions(token: String) {
        viewModelScope.launch {
            var response = repository.getMyTransactions(token)
            transactions.value = response
        }
    }

    fun getMyCoupons(token: String) {
        viewModelScope.launch {
            var response = repository.getMyCoupons(token)
            coupons.value = response
        }
    }

}