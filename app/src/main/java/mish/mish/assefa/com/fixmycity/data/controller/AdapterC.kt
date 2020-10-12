package mish.mish.assefa.com.fixmycity.data.controller

import android.content.Context
import android.graphics.Color
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_card_view.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.data.report.Report

class AdapterC(var arratList: ArrayList<Report>, val context:Context) : RecyclerView.Adapter<AdapterC.ViewHolder>() {
    //var arratList:ArrayList<Report>
    init {
        this.arratList=arratList
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
       val v =LayoutInflater.from(p0.context).inflate(R.layout.custom_card_view,p0,false)


        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return arratList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItems(arratList[p1])

        p0.expandableLayout.visibility


        p0.itemView.setOnClickListener {
            if (p1==0){

            }
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        fun bindItems(report: Report) {
            itemView.title_card_tv.text = report.reportReq?.name
            if (report.report_status) {
                itemView.status_icon_tv.setBackgroundResource(R.drawable.onlineee)
                itemView.status_resolve_tv.text = "Resolved"
                itemView.status_resolve_tv.setTextColor(Color.GREEN)

            } else {
                itemView.status_resolve_tv.text = "Not Resolved"
                itemView.status_resolve_tv.setTextColor(Color.RED)
                itemView.status_icon_tv.setBackgroundResource(R.drawable.offline1)
            }
            itemView.hour_ago_card.text = report.reported_time
            itemView.location_custom_tv.text = report.reportReq?.municipal
            itemView.description_custom_tv.text = report.reportReq?.description

            var expandableLayout: LinearLayout = itemView.expandable_layout_1

            expandableLayout.setOnClickListener {

            }

            }
            var expandableLayout: LinearLayout = itemView.expandable_layout_1


        }


    }


