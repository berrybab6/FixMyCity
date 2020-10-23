package mish.mish.assefa.com.fixmycity.data.report

import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import java.io.Serializable

class Report(reportRequest:ReportReq):Serializable{
   var reportReq: ReportReq = reportRequest

    var expandeble:Boolean=false
    var report_status:Boolean=false
    var reported_time:String="4hr ago"
    var is_rated:Boolean=false



}


/*
fun convertImageToStringForServer(imageBitmap: Bitmap?): String? {
    val stream = ByteArrayOutputStream()
    if (imageBitmap != null) {
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    } else {
        return null
    }
}
*/