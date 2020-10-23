package mish.mish.assefa.com.fixmycity.municipality.activity

import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_other.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.framework.base.BaseActivity
import mish.mish.assefa.com.fixmycity.municipality.controller.MunicipSession
import mish.mish.assefa.com.fixmycity.municipality.data.Municipality
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.HashMap

class LoginOtherActivity : BaseActivity() {
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null
    lateinit var municipality: Municipality
    lateinit var session: MunicipSession



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_other)
        //init
        // val retrofit:Retrofit=RetrofitClient.getInstance()
        retrofitInterface = retrofit!!.create(IMyService::class.java)

        login_other_btn.setOnClickListener {

            val username = login_other_email.text.toString()
            val password = login_other_password.text.toString()
            val map = HashMap<String, String>()
            map["username"] = username
            map["password"] = password


            if (username.isEmpty() || password.isEmpty()) {
                login_other_error.text = "Fields cant be empty"
            } else {
                val call = retrofitInterface!!.executeLoginMun(map)
                call.enqueue(object : Callback<Municipality> {
                    override fun onFailure(call: Call<Municipality>, t: Throwable) {
                        Toast.makeText(this@LoginOtherActivity, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Municipality>, response: Response<Municipality>) {
                        if (response.code() == 200) {
                            val name = response.body()!!.name
                            val username1 = response.body()!!.username
                            val id = response.body()!!._id
                            val password1 = response.body()!!.password

                            Toast.makeText(this@LoginOtherActivity,id,Toast.LENGTH_SHORT).show()

                            municipality = Municipality(
                                name,
                                password1,
                                username1,
                                id
                            )
                            session =
                                MunicipSession(this@LoginOtherActivity)
                            // Creating user login session
                            session.createMunipSession(name,id,password1,username1)
                            //intent
                            val inte = Intent(this@LoginOtherActivity, ReportsMunicipalityActivity::class.java)
                            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                            inte.putExtra("Mun", 5)

                            //inte.flags( Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(inte)

                        } else if (response.code() == 400) {

                            login_other_email.setText("")
                            login_other_password.setText("")
                            login_error.text = "Incorrect Password and/or Email!!!!"
                        }

                    }
                })

            }


        }
    }
}
