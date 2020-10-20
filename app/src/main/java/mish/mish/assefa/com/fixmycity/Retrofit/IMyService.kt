package mish.mish.assefa.com.fixmycity.Retrofit

//import mish.mish.assefa.com.fixmycity.data.user.User


import mish.mish.assefa.com.fixmycity.data.municipality.Municipalities
import mish.mish.assefa.com.fixmycity.data.report.Report
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import mish.mish.assefa.com.fixmycity.data.municipality.Municipality
import mish.mish.assefa.com.fixmycity.data.report.Reports
import mish.mish.assefa.com.fixmycity.data.user.User
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

//import kotlin.collections

//import retrofit2.http.POST

interface IMyService {

    @POST("/auth/login")
    fun executeLogin(@Body map: HashMap<String, String>): Call<User>
    @POST("/auth/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void>


    @POST("/municipality/login")
    fun executeLoginMun(@Body map: HashMap<String, String>):Call<Municipality>

    @POST("/reports")
    fun addReport(@Body map: HashMap<String, String>):Call<ReportReq>
          //@Part file: MultipartBody.Part,
          //@Part("file") image:RequestBody,
          //@Part("body") map:HashMap<String,String>):Call<ReportReq>

    @GET("/municipality/")
    fun getMunicip():Call<ArrayList<String>>

    @POST("/auth/activate/:token")
    fun activateToken(@Field("token") token:Boolean):Call<ActivateResult>

    @POST("/auth/forget-password/")
    fun forgetPassword(
        @Body map: HashMap<String, String>
    ):Call<Void>

    @POST("/auth/reset-password/")
    fun resetPassword(
        @Body @Field("email") email:String
    ):Call<Void>

    @GET("/reports")
    suspend fun getReport(): Report

}


