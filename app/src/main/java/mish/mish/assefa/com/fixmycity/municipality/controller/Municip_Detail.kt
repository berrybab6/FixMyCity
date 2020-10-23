package mish.mish.assefa.com.fixmycity.municipality.controller

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_card_view.view.*
import kotlinx.android.synthetic.main.municipdetail.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.data.report.ReportReq

class Municip_Detail(var arratList: ArrayList<ReportReq>, val context: Context): RecyclerView.Adapter<Municip_Detail.MunicipalityDetailHolder>(){
 val reportsList:ArrayList<ReportReq>
 val _context:Context
    init
    {
        this.reportsList=arratList
        this._context=context
    }

    inner class MunicipalityDetailHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener
    {
        val reportTitle=view.title_card_tv.toString()
        val report_status=view.status_icon_tv
        val report_time=view.hour_ago_card
        override fun onClick(v: View?) {
            val position=adapterPosition
            Toast.makeText(context,"$reportTitle  $position", Toast.LENGTH_SHORT).show()
        }

        init {

            view.setOnClickListener(this)
        }


        fun bindItems(report: ReportReq) {
            itemView.title_card_tv.text = report.name
            if (report.isResolved) {
                //itemView.status_icon_tv.setBackgroundResource(R.drawable.onlineee)
                itemView.isResolved_tv.text = "Resolved"
                itemView.isResolved_tv.setTextColor(Color.GREEN)
                if (report.is_rated){
                    itemView.is_assigned_btn.visibility= VISIBLE
                    itemView.rate_star.visibility= GONE
                    itemView.is_assigned_btn.setBackgroundColor(Color.GREEN)
                }else{
                    itemView.is_assigned_btn.visibility=GONE
                }

            } else {
                itemView.isResolved_tv.text = "Not Resolved"
                itemView.isResolved_tv.setTextColor(Color.RED)
                itemView.is_assigned_btn.setBackgroundResource(R.color.backgroundColor)
                itemView.is_assigned_btn.text="Assign"
                itemView.rate_star.visibility= GONE
                itemView.is_assigned_btn.visibility= VISIBLE
                //itemView.status_icon_tv.setBackgroundResource(R.drawable.offline1)
            }
            itemView.hour_ago_card.text = report.created_at
            itemView.location_municip_tv.text = report.reported_to
            itemView.description_municip_tv.text = report.description

            //var expandableLayout: LinearLayout = itemView.expandable_layout_1

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MunicipalityDetailHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.municipdetail,parent,false)

        return MunicipalityDetailHolder(v)
    }

    override fun getItemCount(): Int {
        return arratList.size
    }

    override fun onBindViewHolder(holder: MunicipalityDetailHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
        holder.bindItems(arratList[position])

    }

}