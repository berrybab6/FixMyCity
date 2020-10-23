package mish.mish.assefa.com.fixmycity.user.data.controller

import android.content.Context
import android.graphics.Color
import android.os.Build
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_card_view.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.data.report.ReportReq

class AdapterC(val context:Context) : RecyclerView.Adapter<ViewHolder>() {
    private var reportResponses = arrayListOf<ReportReq>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
       val v =LayoutInflater.from(p0.context).inflate(R.layout.custom_card_view,p0,false)


        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return reportResponses.size
    }


    fun setReport(responses: ArrayList<ReportReq>) {
        reportResponses = responses
        notifyDataSetChanged()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItems(reportResponses[p1])

        p0.expandableLayout.visibility

        p0.itemView.setOnClickListener {
            if (p1==0){

            }
        }

    }
}

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItems(report: ReportReq) {
            itemView.title_card_tv.text = report.name
            if (report.isResolved) {
                itemView.status_icon_tv.setBackgroundResource(R.drawable.onlineee)
                itemView.status_resolve_tv.text = "Resolved"
                itemView.status_resolve_tv.setTextColor(Color.GREEN)

            } else {
                itemView.status_resolve_tv.text = "Not Resolved"
                itemView.status_resolve_tv.setTextColor(Color.RED)
                itemView.rate_stars_custom.visibility=GONE
                itemView.see_more_tv.visibility= GONE
                itemView.status_icon_tv.setBackgroundResource(R.drawable.offline1)
            }

           // val reportTimeMap=report.getReportDate(report.created_at)
            //val reportTime=reportTimeMap["day"].toString()
            itemView.hour_ago_card.text = "4hr ago"
            //val string=report.photo_url
            //val myBitmap = BitmapFactory.decodeFile(string)
               // val a=report.imageStringToBitmap(report.photo_url!!)

            //itemView.location_image_iv.setImageBitmap(a)
            itemView.location_custom_tv.text = report.reported_to
            itemView.description_custom_tv.text = report.description

            var expandableLayout: LinearLayout = itemView.expandable_layout_1

            }
            var expandableLayout: LinearLayout = itemView.expandable_layout_1

        }



