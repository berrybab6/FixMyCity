package mish.mish.assefa.com.fixmycity


import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_login.*


import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient

import mish.mish.assefa.com.fixmycity.user.data.user.getUserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.HashMap
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
//import androidx.test.orchestrator.junit.BundleJUnitUtils.getResult
import com.google.android.gms.tasks.Task
import mish.mish.assefa.com.fixmycity.framework.base.BaseActivity
import mish.mish.assefa.com.fixmycity.user.data.controller.SessionClass
import mish.mish.assefa.com.fixmycity.user.data.user.User
import mish.mish.assefa.com.fixmycity.municipality.activity.LoginOtherActivity
import mish.mish.assefa.com.fixmycity.user.activity.RequestActivity
import mish.mish.assefa.com.fixmycity.user.activity.SignupActivity
import mish.mish.assefa.com.fixmycity.user.data.user.isLoggedIn


const val RC_SIGN_IN:Int=0
class LoginActivity : BaseActivity() {
    //lateinit  var token:String
    lateinit var callBackManager: CallbackManager
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null

    //Google Sign in

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var session: SessionClass
    //lateinit var user:User
    //val BASE_URL = "http://192.168.1.2:3000" // your Ip Address


    override fun onStart() {
        super.onStart()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
       // Initializing Session Class
        session= SessionClass(this)



        val account = GoogleSignIn.getLastSignedInAccount(this)

        val isLoggedinFb = isLoggedIn()



        when {
            isLoggedinFb -> {
                val inte: Intent = Intent(this@LoginActivity, RequestActivity::class.java)
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                inte.putExtra("Facebook", 3)
                startActivity(inte)
            }

           session.isLoggedIn() -> {
                val inte = Intent(this@LoginActivity, RequestActivity::class.java)
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
               // inte.putExtra("user", isLoggedIn)
                inte.putExtra("Fix", 5)
                startActivity(inte)
            }
            account!=null-> {
                val inte: Intent = Intent(this@LoginActivity, RequestActivity::class.java)
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                inte.putExtra("Google", 8)
                startActivity(inte)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mish.mish.assefa.com.fixmycity.R.layout.activity_login)


        retrofitInterface = retrofit!!.create(IMyService::class.java)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
       mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginpage.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()
            val map = HashMap<String, String>()
            map["email"] = email
            map["password"] = password


            if (email.isEmpty() || password.isEmpty()) {
                login_error.text = "Fields cant be empty"
            } else {
                val call = retrofitInterface!!.executeLogin(map)


                call.enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        //login_error.text("")

                        Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_LONG).show()

                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.code() == 200) {

                            val email1=response.body()!!.email
                            val first_name1=response.body()!!.first_name
                            val last_name1=response.body()!!.last_name
                            val id1=response.body()!!._id
                            val token3=response.body()!!.token
                            val password1=response.body()!!.password
                            val username1=response.body()!!.username

                            session=
                                SessionClass(this@LoginActivity)
                            // Creating user login session
                            session.createLoginSession(first_name1,last_name1,email1,id1,token3,password1,username1)

                        Toast.makeText(this@LoginActivity,"token: $email1",Toast.LENGTH_LONG).show()

                            login_error.text = ""
                            Log.d("ActivityCallback", response.body()?.email)
                            if (session.isLoggedIn()) {
                                val inte = Intent(this@LoginActivity, RequestActivity::class.java)
                                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                                inte.putExtra("Fix", 5)

                                //inte.flags( Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(inte)
                            }
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

        forgot.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

              val intent = Intent(this@LoginActivity, ForgotPActivity::class.java)
                startActivity(intent)
            }

        })


        create_login.setOnClickListener {

            val intent=Intent(this@LoginActivity, SignupActivity::class.java)

            startActivity(intent)
        }

        google_sign_in_button.setOnClickListener {
            signIn()
        }

        //Login as Municipality
        login_as_municipality.setOnClickListener {
            val intent=Intent(this, LoginOtherActivity::class.java)
            startActivity(intent)
        }
        //Login With Facebook

        callBackManager= CallbackManager.Factory.create()

        login_button_fb.setReadPermissions(listOf("public_profile", "email"))
        login_button_fb.registerCallback(callBackManager,object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                getUserProfile(
                    result?.accessToken,
                    result?.accessToken?.userId
                )
                val intent=Intent(this@LoginActivity, RequestActivity::class.java)
                intent.putExtra("Facebook",3)
                startActivity(intent)

            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //facebook call Manager
        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        //Google request code
        if (RC_SIGN_IN == requestCode) {
            Log.d("dd", RC_SIGN_IN.toString())
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
                val intent=Intent(this@LoginActivity, RequestActivity::class.java)
                intent.putExtra("Google",8)
                startActivity(intent)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.statusCode)

        }

    }




    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent

        startActivityForResult(signInIntent, RC_SIGN_IN)
    }





}

