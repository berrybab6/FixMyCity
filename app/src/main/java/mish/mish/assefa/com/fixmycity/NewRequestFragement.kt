package mish.mish.assefa.com.fixmycity

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragement_new_request.*
import mish.mish.assefa.com.fixmycity.Retrofit.Report

class NewRequestFragement:Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_new_request, container, false)

        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val arrayList=ArrayList<Report>()
        val c= Report()
        val a=Report()

        c.reportName="Dead Animal"
        c.location="Addis Ketema"
        c.desc="A dog died here in Saris"
       // c.image="asas"

        c.report_status=false
        c.reported_time="4hr ago"
        a.reportName="Pothole"
        a.location="Saris"
        a.desc="A dog died here in Saris"
        a.image="asas"
        a.report_status=true
        a.reported_time="2hr ago"
        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)

        val myAdapter=AdapterC(arrayList,this.requireContext())
        recycler_new_reports.layoutManager=LinearLayoutManager(this.requireContext())
        recycler_new_reports.adapter=myAdapter
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