package mish.mish.assefa.com.fixmycity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragement_new_request.*
import kotlinx.android.synthetic.main.fragement_recent.*
import mish.mish.assefa.com.fixmycity.Retrofit.Report

class RecentFragement:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragement_recent,container,false)
       //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val arrayList=ArrayList<Report>()
        val c= Report()
        val a= Report()

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
        a.reported_time="7hr ago"
        for (i in a.reported_time){

        }

        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)
        arrayList.add(a)
        arrayList.add(c)

   val rList=ArrayList<Report>()
for (j in arrayList.size-1 downTo 0) {

        val a =arrayList[j]
        val b=a.reported_time
        for (i in b){
            if (i.isDigit()&& i.toInt()<5){
                rList.add(a)

            }
        }


}

        val myAdapter=AdapterC(rList,this.requireContext())
        recycler_recent_reports.layoutManager= LinearLayoutManager(this.requireContext())
        recycler_recent_reports.adapter=myAdapter

    }


}