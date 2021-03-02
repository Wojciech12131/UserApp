package pl.pk.myUserApp.repo

import pl.pk.myUserApp.data.*
import pl.pk.myUserApp.service.RetrofitInstance
import retrofit2.Response
import java.io.Serializable
import java.util.*

class Repository : Serializable {

    suspend fun loginPost(loginBody: LoginBody): Response<LoginResponse> {
        return RetrofitInstance.api.loginPost(loginBody.username, loginBody.password, loginBody.code)
    }

    suspend fun registrationPost(registrationBody: RegistrationBody): Response<Objects> {
        return RetrofitInstance.api.registration(registrationBody)
    }

    suspend fun getAboutMe(token: String): Response<AboutMe> {
        return RetrofitInstance.api.getAboutMe("Bearer $token")
    }

    suspend fun getMyTransactions(token: String): Response<List<Transactions>>{
        return RetrofitInstance.api.getMyTransactions("Bearer $token")
    }

    suspend fun getMyCoupons(token: String):Response<List<CouponDetails>>{
        return RetrofitInstance.api.getMyCoupons("Bearer $token")
    }

}