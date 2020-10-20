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
import mish.mish.assefa.com.fixmycity.data.report.ReportReq

//import mish.mish.assefa.com.fixmycity.data.report.Report
//import mish.mish.assefa.com.fixmycity.data.controller.AdapterC

class RecentFragement: Fragment() {
    lateinit var reportReq:ReportReq
    lateinit var report:Report
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragement_recent,container,false)
       activity?.title="Recent Reports"
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arrayList=ArrayList<Report>()
        reportReq= ReportReq("A Dog died here in Saris","Dead Animal","Bole Sub City","asasas","Meti")
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

        val myAdapter= AdapterC(arrayList, this.requireContext())
        view?.recycler_recent_reports?.layoutManager= LinearLayoutManager(this.requireContext())
        view?.recycler_recent_reports?.adapter=myAdapter
    }

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
        val myAdapter= AdapterC(arrayList, this.requireContext())
        view?.recycler_recent_reports?.layoutManager= LinearLayoutManager(this.requireContext())
        view?.recycler_recent_reports?.adapter=myAdapter

    }


}