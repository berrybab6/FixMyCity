package mish.mish.assefa.com.fixmycity.user.activity

import android.os.Bundle
import android.util.Log
//import androidx.core.app.Fragment
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragement_new_request.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.Retrofit.IMyService
import mish.mish.assefa.com.fixmycity.Retrofit.RetrofitClient
import mish.mish.assefa.com.fixmycity.user.data.controller.AdapterC
import mish.mish.assefa.com.fixmycity.data.report.Report
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import mish.mish.assefa.com.fixmycity.user.data.controller.SessionClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


//@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
class NewRequestFragement: Fragment(){

    lateinit var reportReq: ReportReq
    lateinit var report: Report
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view:View=inflater.inflate(R.layout.fragement_new_request, container, false)
        activity?.title = "New Reports"
        return view
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
    /*

    fun getUsers(): ArrayList<Report> {
        val userlist = service.users()
        userlist.enqueue(object : Callback<List<Report>>() {
            fun onResponse(response: Response<List<Report>>) {
                if (response.isSuccess()) {
                    users = response.body()
                    callback.resultReady(users)
                }
            }

            fun onFailure(t: Throwable) {
                Log.e("REST", t.message)
            }
        })
        return users
    }*/

    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    var retrofitInterface: IMyService? = null
    lateinit var session: SessionClass
    lateinit var user_id:String

    lateinit var myAdapter: AdapterC
    override fun onStart() {
        super.onStart()

        session= SessionClass(this.requireContext())
        val user = session.getUserDetails()
        user_id=user.getValue(session.KEY_ID)
        var  user_id2=""
        val sb = StringBuilder()
        //sb.append(a).append(b)
        //val c = sb.toString()
        if (user_id.isNotEmpty()){
        for(i in user_id){
            if (i=='2'){
                break
            }else{
            sb.append(i)}
        }}
        user_id2=sb.toString()

        retrofitInterface = retrofit!!.create(IMyService::class.java)
        Toast.makeText(this.requireContext(),user_id,Toast.LENGTH_LONG).show()

        recycler_new_reports.layoutManager= LinearLayoutManager(this.requireContext())
        myAdapter= AdapterC(this.requireContext())
        recycler_new_reports.adapter=myAdapter
        if (session.isLoggedIn()) {
            getAllReports()
        }



     /*
        val arrayList=ArrayList<ReportReq>()

       reportReq=ReportReq()

        reportReq.reported_by="bedatuassefa@gmail.com"
        reportReq.photo_url="dd"
        reportReq.name="Dead Animal"
        reportReq.reported_to="Bole Sub City Administration"
        reportReq.description="Lorem Ipsum erek earn kgk pl ogk oks adel o mj safaris screwier ideal n,cabin jeff faff"
        //reportReq.expandeble=false
        reportReq.created_at="8hr ago"
        reportReq.report_status=false

        for(i in 0..15){
        arrayList.add(reportReq)}




        recycler_new_reports?.layoutManager= LinearLayoutManager(this.activity)
        recycler_new_reports?.adapter=myAdapter
        recycler_new_reports.setOnClickListener {
                Toast.makeText(this.requireContext(),"onclick is workin i guess",Toast.LENGTH_SHORT).show()
        }*/

    }


    private fun getAllReports(){
        //val map=HashMap<String,String>()
       // map["user_id"]=user_id
        val allReports=retrofitInterface!!.getAllReports(user_id)
        allReports.enqueue(object : Callback<ArrayList<ReportReq>> {
            override fun onFailure(call: Call<ArrayList<ReportReq>>, t: Throwable) {

            }

            override fun onResponse(call: Call<ArrayList<ReportReq>>, response: Response<ArrayList<ReportReq>>) {
                //To change body of created functions use File | Settings | File Templates.
                if (response.isSuccessful){
                    //Toast.makeText(activity,"user_id",Toast.LENGTH_LONG).show()
                    val reportList = response.body()!!


                    myAdapter.setReport(reportList)
                    recycler_new_reports.adapter=myAdapter

                    Log.e("Success",response.body().toString())
                }
            }


        })
    }

    fun getAllMunicipality(){
        val allReports=retrofitInterface!!.getMunicip()
    }

}


 /*

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.nav_menu,menu)
     val search=menu?.findItem(R.id.nav_search_icn)
        val searchView=search?.actionView as SearchView
        searchView.queryHint="Search something"

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
                //TODO("not implemented")
                // To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
                //TODO("not implemented")
                // To change body of created functions use File | Settings | File Templates.
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)


}*/