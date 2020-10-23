package mish.mish.assefa.com.fixmycity.user.activity

//import androidx.core.app.Fragment
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v4.app.Fragment

//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragement_recent.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.user.data.controller.AdapterC
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import mish.mish.assefa.com.fixmycity.user.data.controller.SessionClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

//import mish.mish.assefa.com.fixmycity.data.report.Report
//import mish.mish.assefa.com.fixmycity.user.data.controller.AdapterC

class RecentFragement: Fragment() {
//    lateinit var reportReq: ReportReq
    //lateinit var report: Report
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragement_recent, container, false)
        activity?.title = "Recent Reports"
        return view
    }
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //session = SessionClass(this.requireContext())
        //val user = session.getUserDetails()
        //user_id = user.getValue(session.KEY_ID)

        retrofitInterface = retrofit!!.create(IMyService::class.java)

        recycler_recent_reports.layoutManager = LinearLayoutManager(this.requireContext())
        myAdapter = AdapterC(this.requireContext())
        recycler_recent_reports.adapter = myAdapter
        //if (session.isLoggedIn()) {

            getAllReports()



    }
*/

    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null
    lateinit var session: SessionClass
    lateinit var user_id: String
    lateinit var myAdapter: AdapterC
    override fun onStart() {
        super.onStart()

       session = SessionClass(this.requireContext())
        val user = session.getUserDetails()
        user_id = user.getValue(session.KEY_ID)

        retrofitInterface = retrofit!!.create(IMyService::class.java)

        recycler_recent_reports.layoutManager = LinearLayoutManager(this.requireContext())
        myAdapter = AdapterC(this.requireContext())
        recycler_recent_reports.adapter = myAdapter
        if (session.isLoggedIn()) {

            getAllReports()

        }

    }

    private fun getAllReports() {
       // val map = HashMap<String, String>()
       // map["user_id"] = user_id
        val allReports = retrofitInterface!!.getAllReports(user_id)
        allReports.enqueue(object : Callback<ArrayList<ReportReq>> {
            override fun onFailure(call: Call<ArrayList<ReportReq>>, t: Throwable) {

            }

            override fun onResponse(call: Call<ArrayList<ReportReq>>, response: Response<ArrayList<ReportReq>>) {
                //To change body of created functions use File | Settings | File Templates.
                if (response.isSuccessful) {
                    Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show()
                    val reportList = response.body()!!
                    myAdapter.setReport(reportList)
                    recycler_recent_reports.adapter = myAdapter

                    Log.e("Success", response.body().toString())
                }
            }


        })
    }
}