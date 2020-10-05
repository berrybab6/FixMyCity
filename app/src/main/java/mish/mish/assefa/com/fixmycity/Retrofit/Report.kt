package mish.mish.assefa.com.fixmycity.Retrofit

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.ContentResolverCompat
import android.util.Base64
import android.util.Base64.encodeToString
import java.io.ByteArrayOutputStream
import java.util.*

class Report{
    var reportName:String=""
    var desc:String=""
    var image:String =""
    var location:String=""
    var expandeble:Boolean=false
    var reported_by:String=""
    var municip_by:String=""
    var report_status:Boolean=false
    var reported_time:String="4hr ago"



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