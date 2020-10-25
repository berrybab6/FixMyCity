package mish.mish.assefa.com.fixmycity.municipality.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_municipality_detail.*
import kotlinx.android.synthetic.main.change_password.view.*
import kotlinx.android.synthetic.main.notification.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.municipality.controller.Municip_Detail
import mish.mish.assefa.com.fixmycity.municipality.controller.MunicipSession
import mish.mish.assefa.com.fixmycity.data.report.Report
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import mish.mish.assefa.com.fixmycity.framework.base.BaseActivity
import java.util.regex.Pattern

class MunicipalityDetailActivity : BaseActivity() {
    private lateinit var reportReq: ReportReq
    //lateinit var report: Report
    lateinit var sessionClass: MunicipSession

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" + "$"
    )


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout_menu -> {
                //  signOut()
                val i = intent.extras
                if(i?.getInt("Mun",1)==5){
                    sessionClass= MunicipSession(this)
                    val user=sessionClass.getMunipDetails()
                    val userName=user.getValue(sessionClass.KEY_USERNAME)
                    Toast.makeText(this,userName, Toast.LENGTH_SHORT).show()
                    sessionClass.logoutUser()
                }else{

                    Toast.makeText(this@MunicipalityDetailActivity,"why does it is not working",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_notif_icn -> {
                val view: View = LayoutInflater.from(this@MunicipalityDetailActivity).inflate(R.layout.notification, null)
                val builder2: AlertDialog.Builder = AlertDialog.Builder(this@MunicipalityDetailActivity)
                builder2.setView(view)
                builder2.create().show()
                view.notification_desc.text="Started in 2010, Ristorante con Fusion quickly established itself as a culinary icon par excellence in Hong Kong. With its unique brand of world fusion cuisine that can be found nowhere else, it enjoys patronage from the A-list clientele in Hong Kong."
                view.notification_ok.setOnClickListener {
                    val intent= Intent(this,
                        MunicipalityDetailActivity::class.java)
                    startActivity(intent)
                }
                val intent= Intent(this@MunicipalityDetailActivity,
                    ReportsMunicipalityActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
            }
            R.id.changepassword_menu -> {
                val view: View = LayoutInflater.from(this@MunicipalityDetailActivity).inflate(R.layout.change_password, null)
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MunicipalityDetailActivity)
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

                        //val email: String = "mish@gmail.com"
                        Toast.makeText(this@MunicipalityDetailActivity, "Change Password", Toast.LENGTH_SHORT).show()


                    }
                }
            }
            else -> super.onOptionsItemSelected(item)

        }
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_municipality_detail)

        val intent=intent.extras
        val arrayList=ArrayList<ReportReq>()

        reportReq= ReportReq()
        reportReq.reported_by="bedatuassefa@gmail.com"
        reportReq.photo_url="dd"
        reportReq.name="Pothole"
        reportReq.reported_to="Bole Sub City Administration"
        reportReq.description="Lorem Ipsum erek earn kgk pl ogk oks adel o mj safaris screwier ideal n,cabin jeff faff"
        //reportReq.expandeble=false
        reportReq.created_at="8hr ago"
        reportReq.report_status=false

        for(i in 0..15){
            arrayList.add(reportReq)}

        this.title=intent.getString("data")
        val myAdapter= Municip_Detail(arrayList, this)

        recycler_munip_detail.layoutManager= LinearLayoutManager(this)
        recycler_munip_detail.adapter=myAdapter
        recycler_munip_detail.setOnClickListener {
            Toast.makeText(this@MunicipalityDetailActivity,"onclick is working i guess", Toast.LENGTH_SHORT).show()
        }
    }
}
