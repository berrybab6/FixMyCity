package mish.mish.assefa.com.fixmycity.user.activity

import android.content.Intent
import android.os.Build
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import mish.mish.assefa.com.fixmycity.LoginActivity
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.Retrofit.ActivateResult
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.framework.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.regex.Pattern

class SignupActivity : BaseActivity() {
    val PASSWORD_PATTERN:Pattern=  Pattern.compile("^" +
            "(?=.*[a-zA-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" + "$")

private var retrofit: Retrofit? = RetrofitClient.getInstance()
    private var retrofitInterface: IMyService? = null
   // private val BASE_URL = "http://192.168.1.2:3000"

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun notValidateEmail():Boolean{
        if (!Patterns.EMAIL_ADDRESS.matcher(email_signup_etv.text.toString()).matches()){
            signup_error_tv.text="Please enter a valid Email Address"
        }
        return true
    }

        val active=ActivateResult()
    @RequiresApi(Build.VERSION_CODES.FROYO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        retrofitInterface = retrofit!!.create(IMyService::class.java)


        create_account.setOnClickListener {
            val f_name=firstname_etv.text.toString()
            val l_name=lastname_etv.text.toString()
            val email=email_signup_etv.text.toString()
            val password=password_signup_etv.text.toString()


            val map = HashMap<String, String>()

            map["first_name"] =f_name
            map["last_name"]=l_name
            map["email"] = email
            map["password"] = password
            val conf=confirm_password_etv.text.toString()
            if (email.isEmpty()||password.isEmpty()||f_name.isEmpty()||l_name.isEmpty()||conf.isEmpty()){
                signup_error_tv.text="Fields Can't be Empty"
            }

           else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                signup_error_tv.text="Please enter a valide Email Address"
            }
        else if(!PASSWORD_PATTERN.matcher(password).matches()){
                signup_error_tv.text="Password to weak"
            }

        else if(map["password"]==conf){
            val call = retrofitInterface!!.executeSignup(map)

                call.enqueue(object : Callback<Void> {

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        signup_error_tv.setText("")

                        Toast.makeText(this@SignupActivity, "Email sent successfully check your email", Toast.LENGTH_LONG).show()
                        val inte = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(inte)

                    } else if (response.code() == 400) {
                        email_signup_etv.setText("")
                        password_signup_etv.setText("")
                        firstname_etv.setText("")
                        confirm_password_etv.setText("")
                        signup_error_tv.setText(response.message())
                        //Toast.makeText(this@SignupActivity, "Already Registered ", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        this@SignupActivity, t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        }else{
            signup_error_tv.text="Password must match!!!"
            password_signup_etv.setText("")
            confirm_password_etv.setText("")

        }

        }
        login_create.setOnClickListener {
            val inte = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(inte)
        }

}
}


/*
        val retrofit: Retrofit = RetrofitClient.getInstance()
        iMyService=retrofit.create(IMyService::class.java)

        create_account.setOnClickListener(object: View.OnClickListener{

            override fun onClick(v: View?) {
                val email=email_signup_etv.text.toString()
                val password=password_signup_etv.text.toString()

                val name:String=firstname_etv.text.toString()

                registerUser(email,name,password)



                //val inte = Intent(this@SignupActivity, SignupActivity::class.java)
                //startActivity(inte)
            }


        })*/


/*
    private fun registerUser(emaill:String,namee:String,passwordd:String){
        val email=email_signup_etv.text.toString()
        val password=password_signup_etv.text.toString()
        val confirm:String=confirm_password_etv.text.toString()
        val name:String=firstname_etv.text.toString()
        if (email.isEmpty()||password.isEmpty()||confirm.isEmpty()||name.isEmpty()){
            Toast.makeText(this@SignupActivity," inputField can not be empty", Toast.LENGTH_SHORT).show()
        }else if(password != confirm){
            Toast.makeText(this@SignupActivity," password must match with Confirm Password", Toast.LENGTH_SHORT).show()
        }


        compositeDisposable.add(iMyService.registerUser(emaill,namee, passwordd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result->
                Toast.makeText(this@SignupActivity," "+result, Toast.LENGTH_SHORT).show()
            }
        )
    }
}
*/