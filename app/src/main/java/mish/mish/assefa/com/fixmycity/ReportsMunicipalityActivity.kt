package mish.mish.assefa.com.fixmycity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_reports_municipality.*
import kotlinx.android.synthetic.main.fragement_new_request.*
import mish.mish.assefa.com.fixmycity.data.controller.AdapterC
import mish.mish.assefa.com.fixmycity.data.controller.Municipality_1
import mish.mish.assefa.com.fixmycity.data.report.Report
import mish.mish.assefa.com.fixmycity.data.report.ReportReq

class ReportsMunicipalityActivity : AppCompatActivity() {
    private lateinit var reportReq:ReportReq
    private lateinit var report:Report
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports_municipality)
        val arrayList=ArrayList<Report>()


        reportReq= ReportReq("A Dog died here in Saris","Dead Animal","Bole Sub City","asasas","Meti")
        report=Report(reportReq)
        report.expandeble=false
        report.reported_time="8hr ago"
        report.report_status=false

        for (i in 0..15){
            arrayList.add(report)
        }

        val myAdapter= Municipality_1(arrayList,this@ReportsMunicipalityActivity)

        recycler_munip_1?.layoutManager= LinearLayoutManager(this)
        recycler_munip_1?.adapter=myAdapter

    }

}
