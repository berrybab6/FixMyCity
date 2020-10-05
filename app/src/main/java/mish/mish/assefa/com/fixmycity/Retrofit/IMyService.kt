package mish.mish.assefa.com.fixmycity.Retrofit

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody


import java.util.HashMap

import retrofit2.Call
import retrofit2.http.*

//import retrofit2.http.POST

interface IMyService {

    @POST("/auth/login")
    fun executeLogin(@Body map: HashMap<String, String>): Call<LoginResult>
    @POST("/auth/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void>


    @POST("/reports/")
    fun addReport(
        @Path ("image") image:String,
        @Body map:HashMap<String,String>):Call<Report>

    @POST("/auth/activate/:token")
    fun activateToken(@Field("token") token:Boolean):Call<ActivateResult>

    @POST("/auth/forget-password/")
    fun forgetPassword(
        @Body @Field("email") email:String
    ):Call<Void>

    @POST("/auth/reset-password/")
    fun resetPassword(
        @Body @Field("email") email:String
    ):Call<Void>

    @GET("/reports/")
    suspend fun getReport():Report

}

/*
interface IMyService {
    @POST("signup")
    @FormUrlEncoded
    fun registerUser(@Field("email") email:String,
                     @Field("name") name:String,
                     @Field("password") password:String
    ):Observable<String>

    @POST("login")
    @FormUrlEncoded
    fun loginUser(@Field("email") email:String,
                     @Field("password") password:String
    ):Observable<String>
}*/