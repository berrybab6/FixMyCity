package mish.mish.assefa.com.fixmycity


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*


import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.LoginResult
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.Retrofit.SessionManagement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap


class LoginActivity : AppCompatActivity() {
   //lateinit  var token:String
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
     var retrofitInterface: IMyService? = null
    //val BASE_URL = "http://192.168.1.2:3000" // your Ip Address


    override fun onStart() {
        super.onStart()
        val sessionManagement=SessionManagement(this)
        val isLoggedIn:String=sessionManagement.getSession()
        if (isLoggedIn.isNotEmpty()){
            val inte:Intent = Intent(this@LoginActivity, RequestActivity::class.java)
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            //inte.flags( Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(inte)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()*/
        retrofitInterface = retrofit!!.create(IMyService::class.java)


        loginpage.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                val email = login_email.text.toString()
                val password = login_password.text.toString()

                val map = HashMap<String, String>()
                map["email"] = email
                map["password"] = password


                if (email.isEmpty() || password.isEmpty()) {
                    login_error.text = "Fields cant be empty"
                } else {
                    val call = retrofitInterface!!.executeLogin(map)


                    call.enqueue(object : Callback<LoginResult> {
                        override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                            //login_error.text("")
                            login_error.text = t.message

                        }

                        override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                            if (response.code() == 200) {
                                val user=LoginResult(response.body()!!.first_name,response.body()!!.last_name,response.body()!!._id,response.body()!!.username,response.body()!!.email,response.body()!!.password
                                ,response.body()!!.token)
                                val sessionManagement:SessionManagement= SessionManagement(this@LoginActivity)
                                sessionManagement.saveSession(user)


                                login_error.text = ""
                                Log.d("ActivityCallback", response.body()?.email)
                                val inte:Intent = Intent(this@LoginActivity, RequestActivity::class.java)
                                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                                //inte.flags( Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(inte)
                                login_email.setText("")
                                login_password.setText("")


                            } else if (response.code() == 400) {
                                val email = login_email.text.toString()
                                val password = login_password.text.toString()
                                if (email.isEmpty() || password.isEmpty()) {
                                    login_error.text = "Fields cannot be empty"
                                } else {
                                    login_email.setText("")
                                    login_password.setText("")
                                    login_error.text = "Incorrect Password and/or Email!!!!"
                                }

                            }


                        }


                    })


                }
            }
        })

        forgot.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

              val intent = Intent(this@LoginActivity, ForgotPActivity::class.java)
                startActivity(intent)
            }

        })

        create_login.setOnClickListener {

            val intent=Intent(this@LoginActivity,SignupActivity::class.java)
            //val intent= Intent(,CreateAccount::class.java)
            //=new Intent(SplashActivity.this,MainActivity.class)
            startActivity(intent)
        }

        login_google_iv.setOnClickListener {
            val intent=Intent(this@LoginActivity,LoginOtherActivity::class.java)

            startActivity(intent)
        }

        login_tw_iv.setOnClickListener {
            val intent=Intent(this@LoginActivity,LoginOtherActivity::class.java)

            startActivity(intent)
        }
        login_fb_iv.setOnClickListener {
            val intent=Intent(this@LoginActivity,LoginOtherActivity::class.java)

            startActivity(intent)
        }
    }
}


/*
    private fun handleSignupDialog() {
        val view = layoutInflater.inflate(R.layout.signup_dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(view).show()

        val signupBtn = view.findViewById(R.id.signup)
        val nameEdit = view.findViewById(R.id.nameEdit)
        val emailEdit = view.findViewById(R.id.emailEdit)
        val passwordEdit = view.findViewById(R.id.passwordEdit)

        signupBtn.setOnClickListener(View.OnClickListener {
            val map = HashMap<String, String>()
            map["name"] = nameEdit.getText().toString()
            map["email"] = emailEdit.getText().toString()
            map["password"] = passwordEdit.getText().toString()

            val call = retrofitInterface!!.executeSignup(map)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        Toast.makeText(this@MainActivity, "Signed Up successfully", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        Toast.makeText(this@MainActivity, "Already Registered ", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity, t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        })
    }
}
*/
/*
class LoginActivity : AppCompatActivity() {
    lateinit var iMyService: IMyService
    internal var compositeDisposable=CompositeDisposable()

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //init
        val retrofit:Retrofit=RetrofitClient.getInstance()
        iMyService=retrofit.create(IMyService::class.java)

        //
        loginpage.setOnClickListener(object:View.OnClickListener{

            override fun onClick(v: View?) {
                val email=login_email.text.toString()
                val password=login_password.text.toString()


                    loginUser(email,password)



                    //val inte = Intent(this@LoginActivity, SignupActivity::class.java)
                    //startActivity(inte)
                }


        })

        create_login.setOnClickListener {

            val intent=Intent(this@LoginActivity,SignupActivity::class.java)
            //val intent= Intent(,CreateAccount::class.java)
            //=new Intent(SplashActivity.this,MainActivity.class)
            startActivity(intent)
        }
        forgot.setOnClickListener(object:View.OnClickListener{
               override fun onClick(v: View?) {

                intent = Intent(this@LoginActivity, ForgotPActivity::class.java)
            }

        })
        login_google_iv.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

                intent = Intent(this@LoginActivity, LoginOtherActivity::class.java)
            }
        })

    }

    private fun loginUser(email:String,password:String){
        if (login_email.text.isEmpty() || login_password.text.isEmpty()){
            login_error.text="Incorrect Email and/or Incorrect Password !!!"}

        compositeDisposable.add(iMyService.loginUser(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result->
                Toast.makeText(this@LoginActivity,"hey "+result,Toast.LENGTH_SHORT).show()
                Log.d("ActivityCallBack","User signed in Succesfully")
            }
        )
        //Log.d("ActivityCallBack","User signed in Succesfully")
    }
}*/