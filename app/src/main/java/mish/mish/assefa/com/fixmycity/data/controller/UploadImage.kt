package mish.mish.assefa.com.fixmycity.data.controller

import okhttp3.CipherSuite.Companion.forJavaName
import okhttp3.MediaType.Companion.toMediaType
import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
/*
class UploadImage(
    private val file: File){
   // class UploadRequestBody(
        //private val file: File
     //  private val contentType: String
       // private val callback: UploadCallback
    //) : RequestBody() {


        //override fun contentType() = MediaType.parse("$contentType/*")

        override fun contentLength() = file.length()

        override fun writeTo(sink: BufferedSink) {
            val length = file.length()
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            val fileInputStream = FileInputStream(file)
            var uploaded = 0L
            fileInputStream.use { inputStream ->
                var read: Int
                val handler = Handler(Looper.getMainLooper())
                while (inputStream.read(buffer).also { read = it } != -1) {
                    //handler.post(ProgressUpdater(uploaded, length))
                    uploaded += read
                    sink.write(buffer, 0, read)
                }
            }
        }

        interface UploadCallback {
            fun onProgressUpdate(percentage: Int)
        }

        inner class ProgressUpdater(
            private val uploaded: Long,
            private val total: Long
        ) : Runnable {
            override fun run() {
                callback.onProgressUpdate((100 * uploaded / total).toInt())
            }
        }

        companion object {
            private const val DEFAULT_BUFFER_SIZE = 2048
        }
    }
}*/