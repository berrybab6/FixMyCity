package mish.mish.assefa.com.fixmycity.municipality.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_reports_municipality.*
import kotlinx.android.synthetic.main.change_password.view.*
import kotlinx.android.synthetic.main.notification.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.municipality.controller.Clicked
import mish.mish.assefa.com.fixmycity.municipality.controller.Municipality_1
import mish.mish.assefa.com.fixmycity.municipality.controller.MunicipSession
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import mish.mish.assefa.com.fixmycity.framework.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.regex.Pattern

class ReportsMunicipalityActivity : BaseActivity(),
    Clicked {

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

                    Toast.makeText(this@ReportsMunicipalityActivity,"why does it is not working",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.nav_notif_icn -> {
                val view: View = LayoutInflater.from(this@ReportsMunicipalityActivity).inflate(R.layout.notification, null)
                val builder2: AlertDialog.Builder = AlertDialog.Builder(this@ReportsMunicipalityActivity)
                builder2.setView(view)
                builder2.create().show()
                view.notification_desc.text="Started in 2010, Ristorante con Fusion quickly established itself as a culinary icon par excellence in Hong Kong. With its unique brand of world fusion cuisine that can be found nowhere else, it enjoys patronage from the A-list clientele in Hong Kong."
                view.notification_ok.setOnClickListener {
                    val intent= Intent(this,
                        ReportsMunicipalityActivity::class.java)
                    startActivity(intent)
                }
                /*val intent= Intent(this@ReportsMunicipalityActivity,
                    ReportsMunicipalityActivity::class.java)
                startActivity(intent)*/
                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
            }
            R.id.changepassword_menu -> {
                val view: View = LayoutInflater.from(this@ReportsMunicipalityActivity).inflate(R.layout.change_password, null)
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@ReportsMunicipalityActivity)
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
                        Toast.makeText(this@ReportsMunicipalityActivity, "Change Password", Toast.LENGTH_SHORT).show()


                    }
                }
            }
            else -> super.onOptionsItemSelected(item)

        }
        return true
    }
    override fun clickedReport(report: ReportReq, position: Int) {
        Toast.makeText(this@ReportsMunicipalityActivity,report.name,Toast.LENGTH_SHORT).show()
        /*val intent= Intent(this@ReportsMunicipalityActivity,MunicipalityDetailActivity::class.java)
        intent.putExtra("data",report)
        startActivity(intent)*/
    }




    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null
    lateinit var municipSession: MunicipSession
    lateinit var municip_id:String

    lateinit var myAdapter: Municipality_1
    //private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports_municipality)
        this.title="Requests"
        municipSession=
            MunicipSession(this@ReportsMunicipalityActivity)
        val municipality = municipSession.getMunipDetails()
       municip_id=municipality.getValue(municipSession.KEY_ID)

        retrofitInterface = retrofit!!.create(IMyService::class.java)

        recycler_munip_1?.layoutManager= LinearLayoutManager(this)
        myAdapter= Municipality_1(
            this,
            this@ReportsMunicipalityActivity
        )
        recycler_munip_1?.adapter=myAdapter
        getAllReport()

    }

    private fun getAllReport(){
        val map=HashMap<String,String>()
        map["municipal_id"]=municip_id
        val allReports=retrofitInterface!!.getReports(municip_id)
        allReports.enqueue(object : Callback<ArrayList<ReportReq>> {
            override fun onFailure(call: Call<ArrayList<ReportReq>>, t: Throwable) {

            }

            override fun onResponse(call: Call<ArrayList<ReportReq>>, response: Response<ArrayList<ReportReq>>) {
                //To change body of created functions use File | Settings | File Templates.
                if (response.isSuccessful){
                    Toast.makeText(this@ReportsMunicipalityActivity,response.message(),Toast.LENGTH_LONG).show()
                    val reportList = response.body()!!
                    myAdapter.setData(reportList)
                    recycler_munip_1.adapter=myAdapter

                    Log.e("Success",response.body().toString())
                }
            }


        })
    }

}
