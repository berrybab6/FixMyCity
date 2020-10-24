package mish.mish.assefa.com.fixmycity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reset_password.*
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.user.data.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ResetPasswordActivity : AppCompatActivity() {

    private var retrofit:Retrofit?=RetrofitClient.getInstance()
    lateinit var myService:IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        reset_btn.setOnClickListener {
            val map=HashMap<String,String>()
            val resetlink=reset_link_edt.text.toString()
            val newpass=new_password_edt.text.toString()
            map["resetLink"]=resetlink
            map["newPass"]=newpass

            myService=retrofit!!.create(IMyService::class.java)
            val call=myService.resetPassword(map)
            call.enqueue(object : Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {

                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                   if(response.code()==200){
                        val intent= Intent(this@ResetPasswordActivity,LoginActivity::class.java)
                       startActivity(intent)
                   }
                }
            })
        }
    }
}
