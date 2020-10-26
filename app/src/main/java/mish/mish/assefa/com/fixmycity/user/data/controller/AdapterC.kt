package mish.mish.assefa.com.fixmycity.user.data.controller

import android.content.Context
import android.graphics.BitmapFactory
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
import kotlinx.android.synthetic.main.fragement_add_report.view.*
//import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import java.io.InputStream
import android.graphics.drawable.Drawable
//import android.R
//import android.R
import android.content.res.Resources
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import mish.mish.assefa.com.fixmycity.R
import org.intellij.lang.annotations.Identifier
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class AdapterC(val context:Context) : RecyclerView.Adapter<ViewHolder>(){


    private var reportResponses = arrayListOf<ReportReq>()
    val _context=context

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
        val item=reportResponses[p1]
        p0.bindItems(item)

       // p0.expandableLayout.visibility
        val uri = "@drawable/expand_less"  // where myresource (without the extension) is the file



        p0.itemView.expand_ib.setOnClickListener {
            p0.itemView.expand_ib.setImageResource(R.drawable.expand_less)
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
              /*  fun String.toDate(): Date {
                    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
                }*/
                //val a=report.updated_at
//                val m=a.toDate()

                itemView.status_resolve_tv.text = "Not Resloved"
                itemView.status_resolve_tv.setTextColor(Color.RED)
                //itemView.rate_stars_custom.visibility=GONE
                itemView.see_more_tv.visibility= GONE
                itemView.status_icon_tv.setBackgroundResource(R.drawable.offline1)
            }

           // val reportTimeMap=report.getReportDate(report.created_at)
            //val reportTime=reportTimeMap["day"].toString()
            val date=LocalDateTime.now()
            val year=date.year
            val hour=date.hour
            val month=date.monthValue
            val day=date.dayOfMonth


            val b=report.created_at!!
            val dayCreated=b.date
            val hourCreated=b.hours
            val monthCreated=b.month+1
            val yearCrested=b.year+1900

            val a=b!!.hours.toString()

            if (dayCreated.toString().equals(day.toString()) &&yearCrested==year&&month==monthCreated) {
                val m=(hour*60)+date.minute
                val n=((hourCreated+3)*60)+b!!.minutes
                val timeAgo= abs(m-n)
                val minTohour=timeAgo/60
                val minMin=timeAgo%60
                val min= abs(date.minute- b.minutes)

                itemView.hour_ago_card.text = "${minTohour}hr:${minMin}min ago"
            }else{
                itemView.hour_ago_card.visibility= GONE
            }
            //itemView.hour_ago_card.text="$b"


            //val string=report.photo_url
            //val myBitmap = BitmapFactory.decodeFile(string)
               // val a=report.imageStringToBitmap(report.photo_url!!)

            //itemView.location_image_iv.setImageBitmap(a)
            //itemView.location_custom_tv.text = report.reported_to
            itemView.description_custom_tv.text = report.description

           // var expandableLayout: LinearLayout = itemView.expandable_layout_1

            }
          //  var expandableLayout: LinearLayout = itemView.expandable_layout_1

        }




