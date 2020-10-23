package mish.mish.assefa.com.fixmycity.user.activity

import android.annotation.SuppressLint
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
//import com.facebook.login.LoginManager
import com.google.android.material.bottomnavigation.BottomNavigationView
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.change_password.view.*
//import kotlinx.android.synthetic.main.change_password.view.*

import java.util.regex.Pattern
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.notification.view.*
import mish.mish.assefa.com.fixmycity.LoginActivity
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.framework.base.BaseActivity
import mish.mish.assefa.com.fixmycity.user.data.controller.SessionClass
import mish.mish.assefa.com.fixmycity.municipality.activity.ReportsMunicipalityActivity
import retrofit2.Retrofit


class RequestActivity : BaseActivity() ,BottomNavigationView.OnNavigationItemSelectedListener {
   lateinit var mGoogleSignInClient:GoogleSignInClient
    lateinit var sessionClass: SessionClass

    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null

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
                    //LoginManager.getInstance().logOut()

                    val intent = Intent(this@RequestActivity, LoginActivity::class.java)
                    Toast.makeText(this,"logout From Facebook",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    }
                else if(i?.getInt("Fix",1)==5){
                    sessionClass= SessionClass(this)
                    val user=sessionClass.getUserDetails()
                    val email=user.getValue(sessionClass.KEY_EMAIL)
                    Toast.makeText(this,email, Toast.LENGTH_SHORT).show()
                    sessionClass.logoutUser()
                }else{

                    Toast.makeText(this@RequestActivity,"why does it is not working",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_notif_icn -> {
                val view: View = LayoutInflater.from(this@RequestActivity).inflate(R.layout.notification, null)
                val builder2: AlertDialog.Builder =AlertDialog.Builder(this@RequestActivity)
                builder2.setView(view)
                builder2.create().show()
                view.notification_desc.text="Started in 2010, Ristorante con Fusion quickly established itself as a culinary icon par excellence in Hong Kong. With its unique brand of world fusion cuisine that can be found nowhere else, it enjoys patronage from the A-list clientele in Hong Kong."
                view.notification_ok.setOnClickListener {
                    val intent=Intent(this, RequestActivity::class.java)
                    startActivity(intent)
                }
                val intent=Intent(this@RequestActivity,
                    ReportsMunicipalityActivity::class.java)
                startActivity(intent)
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



        sessionClass= SessionClass(this)
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
        val i = intent.extras

          //i.getSerializableExtra("user") as User



        supportFragmentManager.beginTransaction().replace(
            R.id.fragement_container,
            NewRequestFragement()
        ).commit()
        //relativee.visibility = View.VISIBLE
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
       navigationView.setOnNavigationItemSelectedListener(this)
        //bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
}
