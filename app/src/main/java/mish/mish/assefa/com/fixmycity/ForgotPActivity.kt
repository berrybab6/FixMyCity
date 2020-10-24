package mish.mish.assefa.com.fixmycity

import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_forgot_p.*
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.framework.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ForgotPActivity : BaseActivity() {
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_p)


        retrofitInterface = retrofit!!.create(IMyService::class.java)
        forgot_btn.setOnClickListener {
            val email=forgot_email_edt2.text.toString()
            val map1= HashMap<String,String>()
            map1["email"]=email

           val call = retrofitInterface!!.forgetPassword(map1)
              call.enqueue(object : Callback<Void> {
                  override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(!response.isSuccessful){
                        Toast.makeText(this@ForgotPActivity,response.message(),Toast.LENGTH_SHORT).show()
                    }
                    else{
                    Toast.makeText(this@ForgotPActivity,response.message(),Toast.LENGTH_SHORT).show()
                    val inte = Intent(this@ForgotPActivity, ResetPasswordActivity::class.java)
                    startActivity(inte)}
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@ForgotPActivity,t.message,Toast.LENGTH_SHORT).show()
                }
            })
    }
    }
}
