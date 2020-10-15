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
import mish.mish.assefa.com.fixmycity.data.controller.SessionManagement

import mish.mish.assefa.com.fixmycity.data.user.getUserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.HashMap
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.R
import android.R.attr.data
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.ApiException
//import androidx.test.orchestrator.junit.BundleJUnitUtils.getResult
import com.google.android.gms.tasks.Task
import mish.mish.assefa.com.fixmycity.data.user.User


const val RC_SIGN_IN:Int=0
class LoginActivity : AppCompatActivity() {



    //lateinit  var token:String
    lateinit var callBackManager: CallbackManager
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null

    //Google Sign in

    lateinit var mGoogleSignInClient: GoogleSignInClient
    //lateinit var user:User
    //val BASE_URL = "http://192.168.1.2:3000" // your Ip Address


    override fun onStart() {
        super.onStart()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val sessionManagement = SessionManagement(this)
        val isLoggedIn: String = sessionManagement.getSession()
        val account = GoogleSignIn.getLastSignedInAccount(this)

        val isLoggedinFb = mish.mish.assefa.com.fixmycity.data.user.isLoggedIn()

        val fN = sessionManagement.getFirstName()
        val lN = sessionManagement.getLastName()
        val userName = sessionManagement.getUserName()
        val password: String = sessionManagement.getPassword()
        val email = sessionManagement.getEmail()
        val id = sessionManagement.getId()
        val token = sessionManagement.getSession()

        val user = User(fN, lN, password, email, id, userName, token)


        when {
            isLoggedinFb -> {
                val inte: Intent = Intent(this@LoginActivity, RequestActivity::class.java)
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                inte.putExtra("Facebook", 3)
                startActivity(inte)
            }
            isLoggedIn.isNotEmpty() -> {
                val user1= user.token

                val inte = Intent(this@LoginActivity, RequestActivity::class.java)
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                inte.putExtra("user", isLoggedIn)
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

    lateinit var user:User
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
                            val _id1=response.body()!!._id
                            val token3=response.body()!!.token
                            val password1=response.body()!!.password
                            val username1=response.body()!!.username
                            user=User(first_name1,last_name1,password1,email1,_id1,username1,token3)


                            val sessionManagement=
                                SessionManagement(this@LoginActivity)
                            sessionManagement.saveSession(user)


                            //val id=sessionManagement.getId()
                            val token=sessionManagement.getSession()



                            login_error.text = ""
                            Log.d("ActivityCallback", response.body()?.email)
                            val inte = Intent(this@LoginActivity, RequestActivity::class.java)
                            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            inte.putExtra("user",token)
                            inte.putExtra("Fix",5)

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

        forgot.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

              val intent = Intent(this@LoginActivity, ForgotPActivity::class.java)
                startActivity(intent)
            }

        })

        create_login.setOnClickListener {

            val intent=Intent(this@LoginActivity,SignupActivity::class.java)

            startActivity(intent)
        }

        google_sign_in_button.setOnClickListener {
            signIn()
        }


        //Login With Facebook

        callBackManager= CallbackManager.Factory.create()

        login_button_fb.setReadPermissions(listOf("public_profile", "email"))
        login_button_fb.registerCallback(callBackManager,object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                getUserProfile(result?.accessToken, result?.accessToken?.userId)
                val intent=Intent(this@LoginActivity,RequestActivity::class.java)
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

        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

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
                val intent=Intent(this@LoginActivity,RequestActivity::class.java)
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

