package mish.mish.assefa.com.fixmycity.data.controller

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_card_view.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.RequestActivity
import mish.mish.assefa.com.fixmycity.data.report.Report

class Municipality_1(var arratList: ArrayList<Report>, val context:Context):RecyclerView.Adapter<Municipality_1.MunicipalityHolder>(){

    inner class MunicipalityHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener
    {
        val reportTitle=view.title_card_tv.toString()
        val report_status=view.status_icon_tv
        val report_time=view.hour_ago_card
        override fun onClick(v: View?) {
                val position=adapterPosition
          Toast.makeText(context,"$reportTitle  $position",Toast.LENGTH_SHORT).show()
        }

        init {
            view.setOnClickListener(this)
        }

        fun bindItems(report: Report){
            itemView.title_card_tv.text = report.reportReq?.name
            if (report.report_status) {
                itemView.status_icon_tv.setBackgroundResource(R.drawable.onlineee)
            } else {
                itemView.status_icon_tv.setBackgroundResource(R.drawable.offline1)
            }
            itemView.hour_ago_card.text = report.reported_time
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Municipality_1.MunicipalityHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view,parent,false)

        return MunicipalityHolder(v)
    }

    override fun getItemCount(): Int {
        return arratList.size
    }

    override fun onBindViewHolder(holder: MunicipalityHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
        holder.bindItems(arratList[position])

    }

}