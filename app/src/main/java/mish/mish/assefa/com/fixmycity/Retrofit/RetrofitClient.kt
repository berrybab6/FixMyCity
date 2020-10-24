package mish.mish.assefa.com.fixmycity.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.xml.datatype.DatatypeConstants.SECONDS
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetrofitClient {
                private var retrofit: Retrofit? = null
                const val BASE_URL = "http://192.168.1.2:1000"

       // val httpLoggingInterceptor=HttpLoggingIntercept
       val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
                //private var instance:Retrofit?=null
                fun getInstance():Retrofit {
                    return if (retrofit == null) {
                        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                retrofit!!
        }else{
            retrofit!!
        }



    }
}
