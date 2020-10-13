package mish.mish.assefa.com.fixmycity

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
//import android.support.design.widget.BottomNavigationView
//import androidx.core.app.Fragment
//import android.support.v7.app.AlertDialog
//import android.support.v7.app.AppCompatActivity
//import android.support.design.widget.BottomNavigationView
//import android.support.v4.app.Fragment
//import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.facebook.login.LoginManager
import com.google.android.material.bottomnavigation.BottomNavigationView
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.change_password.view.*
import kotlinx.android.synthetic.main.fragement_new_request.*
//import kotlinx.android.synthetic.main.change_password.view.*
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.data.controller.SessionManagement
import mish.mish.assefa.com.fixmycity.data.user.User
import retrofit2.Retrofit
import java.util.regex.Pattern
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import mish.mish.assefa.com.fixmycity.data.user.getUserProfile


class RequestActivity : AppCompatActivity() ,BottomNavigationView.OnNavigationItemSelectedListener {
   lateinit var mGoogleSignInClient:GoogleSignInClient
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val selectedFragement = when (p0.itemId) {
            R.id.new_request_icn -> {
                NewRequestFragement()
            }
            R.id.add_request_icn -> AddReportFragement()
            R.id.recent_icn -> RecentFragement()
            else -> NewRequestFragement()
        }
       // getIntent().putExtra("complexObject", user);
        supportFragmentManager.beginTransaction().replace(R.id.fragement_container, selectedFragement).commit()
        return true

    }

    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    private var retrofitInterface: IMyService? = null

    //private lateinit var adapter: ArrayAdapter<String>
    val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" + "$"
    )

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    @SuppressLint("ShowToast")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout_menu -> {

                //  signOut()
                val i = intent.extras
                if (i?.getInt("Google", 0) == 8) {
                    signOut()
                    Toast.makeText(this,"Google logout",Toast.LENGTH_SHORT).show()
                }
                else if(i?.getInt("Facebook",0)==3){
                    LoginManager.getInstance().logOut()

                    val intent = Intent(this@RequestActivity, LoginActivity::class.java)
                    Toast.makeText(this,"logout From Facebook",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    }
                else if(i?.getInt("Fix",1)==5){
                    val sessionManagement = SessionManagement(this)
                    sessionManagement.removeSession()

                    val intent = Intent(this@RequestActivity, LoginActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_notif_icn -> {

                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
            }
            R.id.changepassword_menu -> {
                val view: View = LayoutInflater.from(this@RequestActivity).inflate(R.layout.change_password, null)
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@RequestActivity)
                builder.setView(view)
                builder.create().show()
                val newP: String? = view.new_password_etv.text.toString()
                val oldP: String? = view.old_password_etv.text.toString()
                val confirmP: String? = view.confirm_new_password_etv.text.toString()


                // var a=R.layout.change_password.
                view.change_pass_btn.setOnClickListener {
                    //is Capital
                    //updatePassword(oldP,newP)
                    if (newP == "" || confirmP == "" || oldP == "") {
                        view.change_p_weak_tv.setText("")
                        view.change_p_error_tv.text = "Input fields can not be empty"
                    } else if (newP != confirmP) {
                        view.change_p_error_tv.text = "Password must match !!!"
                    } else if (!PASSWORD_PATTERN.matcher(newP).matches()) {
                        view.change_p_weak_tv.text = "Password too Weak"
                    } else {

                        val email: String = "mish@gmail.com"
                        Toast.makeText(this@RequestActivity, "Change Password", Toast.LENGTH_SHORT).show()


                    }
                }
            }
            else -> super.onOptionsItemSelected(item)

        }
        return true
    }
    //Signout
    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                val intent = Intent(this@RequestActivity, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                //inte.flags( Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto = acct.photoUrl
        }
        val i = intent
        val user: User =User("mimish","assefa","@ab64","bedatuassefa@gmail.com","","","")    //i.getSerializableExtra("user") as User

        i.putExtra("user", user)
        supportFragmentManager.beginTransaction().replace(R.id.fragement_container, NewRequestFragement()).commit()
        //relativee.visibility = View.VISIBLE
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
       navigationView.setOnNavigationItemSelectedListener(this)
        //bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
}
