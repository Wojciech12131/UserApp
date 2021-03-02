package pl.pk.myUserApp.api

import pl.pk.myUserApp.data.*
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ApiInterface {


    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun loginPost(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("code") code: String?
    ): Response<LoginResponse>


    @POST("accounts/registration")
    suspend fun registration(
        @Body registrationBody: RegistrationBody
    ): Response<Objects>

    @GET("accounts/me")
    suspend fun getAboutMe(
        @Header("Authorization") auth: String
    ): Response<AboutMe>

    @GET("accounts/me/transactions")
    suspend fun getMyTransactions(
        @Header("Authorization") auth: String
    ): Response<List<Transactions>>

    @GET("accounts/me/coupons")
    suspend fun getMyCoupons(
        @Header("Authorization") auth:String
    ): Response<List<CouponDetails>>
}