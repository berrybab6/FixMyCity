package mish.mish.assefa.com.fixmycity

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
import androidx.fragment.app.Fragment
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

class RequestActivity : AppCompatActivity() ,BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val selectedFragement = when (p0.itemId) {
            R.id.new_request_icn -> {
                NewRequestFragement()
            }
            R.id.add_request_icn -> AddReportFragement()
            R.id.recent_icn -> RecentFragement()
            else -> NewRequestFragement()
        }
        //getIntent().putExtra("complexObject", user);
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
                val sessionManagement = SessionManagement(this)
                sessionManagement.removeSession()
                val inte: Intent = Intent(this@RequestActivity, MainActivity::class.java)
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                //inte.flags( Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(inte)

                Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_notif_icn -> {

                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
            }
            R.id.changepassword_menu -> {
                val view: View = LayoutInflater.from(this@RequestActivity).inflate(R.layout.change_password, null)
                var builder: AlertDialog.Builder = AlertDialog.Builder(this@RequestActivity)
                builder.setView(view)
                builder.create().show()
                var newP: String? = view.new_password_etv.text.toString()
                var oldP: String? = view.old_password_etv.text.toString()
                var confirmP: String? = view.confirm_new_password_etv.text.toString()


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        val i = intent
        val user: User = i.getSerializableExtra("user") as User

        getIntent().putExtra("user", user)
        supportFragmentManager.beginTransaction().replace(R.id.fragement_container, AddReportFragement()).commit()
        relativee.visibility = View.VISIBLE
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
}
/*

   private val navListener: BottomNavigationView.OnNavigationItemSelectedListener= BottomNavigationView.OnNavigationItemSelectedListener {
        val selectedFragement: Fragment =when(it.itemId){
           R.id.new_request_icn->{
               NewRequestFragement()
           }
           R.id.add_request_icn->AddReportFragement()
           R.id.recent_icn->RecentFragement()
            else->NewRequestFragement()
       }
       //getIntent().putExtra("complexObject", user);
       supportFragmentManager.beginTransaction().replace(R.id.fragement_container,selectedFragement).commit()
       true
    }
}
*/