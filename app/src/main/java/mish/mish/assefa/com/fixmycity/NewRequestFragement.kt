package mish.mish.assefa.com.fixmycity

import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.android.synthetic.main.fragement_new_request.*
import kotlinx.android.synthetic.main.fragement_new_request.view.*
import mish.mish.assefa.com.fixmycity.data.controller.AdapterC
import mish.mish.assefa.com.fixmycity.data.report.Report
import mish.mish.assefa.com.fixmycity.data.report.ReportReq


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
    override fun onStart() {
        super.onStart()
        val arrayList=ArrayList<Report>()

       reportReq=ReportReq("A Dog died here in Saris","Dead Animal","Bole Sub City","asasas","Meti")
       report=Report(reportReq)
        report.expandeble=false
        report.reported_time="8hr ago"
        report.report_status=false

        arrayList.add(report)
        arrayList.add(report)
        arrayList.add(report)
        arrayList.add(report)
        arrayList.add(report)
        arrayList.add(report)

        val myAdapter= AdapterC(arrayList,this.requireActivity())

        recycler_new_reports?.layoutManager= LinearLayoutManager(this.activity)
        recycler_new_reports?.adapter=myAdapter
        recycler_new_reports.setOnClickListener {
                Toast.makeText(this.requireContext(),"onclick is workin i guess",Toast.LENGTH_SHORT).show()
        }

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