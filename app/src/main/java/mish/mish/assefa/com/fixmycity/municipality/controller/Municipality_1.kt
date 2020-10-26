package mish.mish.assefa.com.fixmycity.municipality.controller

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_card_view.view.hour_ago_card
import kotlinx.android.synthetic.main.custom_card_view.view.status_icon_tv
import kotlinx.android.synthetic.main.custom_card_view.view.title_card_tv
import kotlinx.android.synthetic.main.municipdetail.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import java.time.LocalDateTime
import kotlin.math.abs

class Municipality_1(var listener:(ReportReq)->Unit, val context:Context):RecyclerView.Adapter<MunicipalityHolder>() {
    private var reportResponses = arrayListOf<ReportReq>()
    // private lateinit var clickedItem:Clicked


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MunicipalityHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.municip_custom, parent, false)

        return MunicipalityHolder(v)
    }

    override fun getItemCount(): Int {
        return reportResponses.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MunicipalityHolder, position: Int) {
        val item=reportResponses[position]

        holder.bindItems(item) //, clicked)
        holder.itemView.setOnClickListener {
            listener(item)
        }

    }

    fun setData(responses: ArrayList<ReportReq>) {
        reportResponses = responses
        notifyDataSetChanged()
    }
}
    /*interface Clicked {
        fun clickedReport(report: ReportReq,position: Int)
    }

*/

class MunicipalityHolder(itemView: View):RecyclerView.ViewHolder(itemView)//,View.OnClickListener
{

    val reportTitle=itemView.title_card_tv.text.toString()
    val report_status=itemView.status_icon_tv.text.toString()
    val report_time=itemView.hour_ago_card.text.toString()


    @RequiresApi(Build.VERSION_CODES.O)
    fun bindItems(report: ReportReq){//,action: Clicked){
        itemView.title_card_tv.text = report.name
        if (report.isResolved) {
            itemView.status_icon_tv.setBackgroundResource(R.drawable.onlineee)
        } else {
            itemView.status_icon_tv.setBackgroundResource(R.drawable.offline1)
        }

        val date= LocalDateTime.now()
        val year=date.year
        val hour=date.hour
        val month=date.monthValue
        val day=date.dayOfMonth


        val b=report.created_at!!
        val dayCreated=b.date
        val hourCreated=b.hours
        val monthCreated=b.month+1
        val yearCrested=b.year+1900

        //val a=b!!.hours.toString()

        if (dayCreated.toString().equals(day.toString()) &&yearCrested==year&&month==monthCreated) {
            val m=(hour*60)+date.minute
            val n=((hourCreated+3)*60)+b.minutes
            val timeAgo= abs(m-n)
            val minTohour=timeAgo/60
            val minMin=timeAgo%60
            val min= abs(date.minute- b.minutes)

            itemView.hour_ago_card.text = "${minTohour}:${minMin}hr ago"
        }else{
            itemView.hour_ago_card.visibility= View.GONE
        }
        //itemView.hour_ago_card.text = "6hr ago"//report.created_at

        /*
        itemView.setOnClickListener {
            //action.clickedReport(report,adapterPosition)
        }*/

        /*itemView.title_card_tv.setOnClickListener {
                clickedItem.clickedReport(report)
        }*/
    }

}