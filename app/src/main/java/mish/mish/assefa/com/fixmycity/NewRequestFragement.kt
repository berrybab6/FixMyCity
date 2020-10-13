package mish.mish.assefa.com.fixmycity

import android.os.Bundle
//import androidx.core.app.Fragment
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.android.synthetic.main.fragement_new_request.view.*
import mish.mish.assefa.com.fixmycity.data.controller.AdapterC
import mish.mish.assefa.com.fixmycity.data.report.Report


//@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
class NewRequestFragement: Fragment(){
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
        val c= Report()
        val a= Report()
        //val n=ReportReq()


        c.reportReq?.name="Dead Animal"
        c.reportReq?.municipal="Addis Ketema"
        c.reportReq?.description="A dog died here in Saris"
       c.reportReq?.image="asas"

        c.report_status=false
        c.reported_time="4hr ago"
        a.reportReq?.name="Pothole"
        a.reportReq?.municipal="Saris"
        a.reportReq?.description="A dog died here in Saris"
        a.reportReq?.image="asas"
        a.report_status=true
        a.reported_time="2hr ago"
        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)

        val myAdapter= AdapterC(arrayList,this.requireActivity())


           view?.recycler_new_reports?.layoutManager= LinearLayoutManager(this.activity)
        view?.recycler_new_reports?.adapter=myAdapter

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