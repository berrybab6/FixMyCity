package mish.mish.assefa.com.fixmycity.Retrofit

//import mish.mish.assefa.com.fixmycity.user.data.user.User


import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import mish.mish.assefa.com.fixmycity.municipality.data.Municipality
import mish.mish.assefa.com.fixmycity.user.data.user.User
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

//import kotlin.collections

//import retrofit2.http.POST


interface IMyService {

    @POST("/auth/login")
    fun executeLogin(@Body map: HashMap<String, String>): Call<User>
    @POST("/auth/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void>

    @PUT("/auth/reset-password")
    fun resetPassword(@Body map: HashMap<String, String>):Call<User>

    @POST("/municipality/login")
    fun executeLoginMun(@Body map: HashMap<String, String>):Call<Municipality>

    @POST("/reports")
    fun addReport(@Body map: HashMap<String, String>):Call<ReportReq>
       // @Part file: MultipartBody.Part,
        //@Part("file") photo_url: RequestBody,
        //@Part("body") map:HashMap<String,String>):Call<ReportReq>

    @GET("/municipality/")
    fun getMunicip():Call<ArrayList<String>>

   // @FormUrlEncoded
    @GET("/reports/myReport/{id}")
    fun getAllReports(@Path("id") id:String):Call<ArrayList<ReportReq>>

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

    @GET("/reports/reportsForMe/{id}")
    fun getReports(@Path("id") id:String):Call<ArrayList<ReportReq>>

}


