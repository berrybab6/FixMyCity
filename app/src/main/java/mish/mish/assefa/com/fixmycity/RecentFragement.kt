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
import kotlinx.android.synthetic.main.fragement_recent.view.*
import mish.mish.assefa.com.fixmycity.data.controller.AdapterC
import mish.mish.assefa.com.fixmycity.data.report.Report

//import mish.mish.assefa.com.fixmycity.data.report.Report
//import mish.mish.assefa.com.fixmycity.data.controller.AdapterC

class RecentFragement: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragement_recent,container,false)
       //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val arrayList=ArrayList<Report>()
        val c= Report()
        val a= Report()

        c.reportReq?.name="Dead Animal"
        c.reportReq?.municipal="Addis Ketema"
        c.reportReq?.description="A dog died here in Saris"
        // c.image="asas"

        c.report_status=false
        c.reported_time="4hr ago"
        a.reportReq?.name="Pothole"
        a.reportReq?.municipal="Saris"
        a.reportReq?.description="A dog died here in Saris"
        //a.image="asas"
        a.report_status=true
        a.reported_time="7hr ago"
        for (i in a.reported_time){

        }

        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)

  /* val rList=ArrayList<Report>()
for (j in arrayList.size-1 downTo 0) {

        val a =arrayList[j]
        val b=a.reported_time
        for (i in b){
            if (i.isDigit()&& i.toInt()<5){
                rList.add(a)

            }
        }


}*/
/*

        val myAdapter= AdapterC(arrayList, this.requireContext())
        view?.recycler_recent_reports?.layoutManager= LinearLayoutManager(this.requireContext())
        view?.recycler_recent_reports?.adapter=myAdapter
*/
    }


}