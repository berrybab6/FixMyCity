package mish.mish.assefa.com.fixmycity

import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_forgot_p.*
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ForgotPActivity : AppCompatActivity() {
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_p)

        val email:String=forgot_email_edt.text.toString()
        forgot_btn.setOnClickListener {


        val call = retrofitInterface!!.forgetPassword(email)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    val inte = Intent(this@ForgotPActivity, LoginActivity::class.java)
                    startActivity(inte)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {

                }
            })
    }
    }
}
