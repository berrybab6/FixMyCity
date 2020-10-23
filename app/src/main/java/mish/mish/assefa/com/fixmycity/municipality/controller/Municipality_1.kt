package mish.mish.assefa.com.fixmycity.municipality.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_card_view.view.hour_ago_card
import kotlinx.android.synthetic.main.custom_card_view.view.status_icon_tv
import kotlinx.android.synthetic.main.custom_card_view.view.title_card_tv
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.data.report.ReportReq

class Municipality_1(var clicked: Clicked, val context:Context):RecyclerView.Adapter<MunicipalityHolder>() {
    private var reportResponses = arrayListOf<ReportReq>()
    // private lateinit var clickedItem:Clicked


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MunicipalityHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.municip_custom, parent, false)

        return MunicipalityHolder(v)
    }

    override fun getItemCount(): Int {
        return reportResponses.size
    }

    override fun onBindViewHolder(holder: MunicipalityHolder, position: Int) {

        holder.bindItems(reportResponses.get(position), clicked)


    }

    fun setData(responses: ArrayList<ReportReq>) {
        reportResponses = responses
        notifyDataSetChanged()
    }
}
    interface Clicked {
        fun clickedReport(report: ReportReq,position: Int)
    }



class MunicipalityHolder(itemView: View):RecyclerView.ViewHolder(itemView)//,View.OnClickListener
{

    val reportTitle=itemView.title_card_tv.text.toString()
    val report_status=itemView.status_icon_tv.text.toString()
    val report_time=itemView.hour_ago_card.text.toString()


    fun bindItems(report: ReportReq,action: Clicked){
        itemView.title_card_tv.text = report.name
        if (report.isResolved) {
            itemView.status_icon_tv.setBackgroundResource(R.drawable.onlineee)
        } else {
            itemView.status_icon_tv.setBackgroundResource(R.drawable.offline1)
        }
        itemView.hour_ago_card.text = "6hr ago"//report.created_at
        itemView.setOnClickListener {
            action.clickedReport(report,adapterPosition)
        }

        /*itemView.title_card_tv.setOnClickListener {
                clickedItem.clickedReport(report)
        }*/
    }

}