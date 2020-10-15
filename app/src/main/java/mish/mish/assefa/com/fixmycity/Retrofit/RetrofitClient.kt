package mish.mish.assefa.com.fixmycity.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
                private var retrofit: Retrofit? = null

                private const val BASE_URL = "http://192.168.1.2:1000"

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
