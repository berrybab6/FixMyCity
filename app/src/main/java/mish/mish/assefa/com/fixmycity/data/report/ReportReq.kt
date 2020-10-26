package mish.mish.assefa.com.fixmycity.data.report

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReportReq(
    var description: String?,
    var name: String?,
    var reported_to: String?,
    //val photo_url:String=""
    var photo_url: String?,
    var reported_by: String?,

    var isResolved: Boolean = false,
    var report_status: Boolean = false,
    var created_at: Date?=null,
    var is_rated: Boolean = false,
    var updated_at: String ):Serializable{

    fun String.toDate(): Date{
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
    }
}
/*
    fun imageStringToBitmap(string: String): Bitmap? {
       // val myBitmap = BitmapFactory.decodeFile(string.getAbsolutePath())
    val imageBytes = Base64.decode(string, 0)
    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image
    }*/
/*
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): HashMap<String, Int> {

        val now = LocalDateTime.now()
        //val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss.SSS")
        //now.dayOfMonth
        val day=HashMap<String,Int>()
        day["hour"]=now.hour
        day["minute"]=now.minute
        day["year"]=now.year
        day["day"]=now.dayOfMonth
        day["month"]=now.monthValue

        return day
            //format(formatter)
    }*/
/*
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getReportDate(string: String): HashMap<String, Int> {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val now = LocalDateTime.parse( string )
        //val date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE)
        //val now=sdf.parse(string)
        val day=HashMap<String,Int>()

        day["hour"]=now.hour
        day["minute"]=now.minute
        day["year"]=now.year
        day["day"]=now.dayOfMonth
        day["month"]=now.monthValue
        return day
    }*/



