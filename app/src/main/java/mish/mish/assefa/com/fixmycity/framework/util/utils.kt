package mish.mish.assefa.com.fixmycity.framework.util

//package mish.mish.assefa.com.hisab.framework.util

import android.util.Log

private const val TAG="FIXMYCITY-TAG-DEBUG"
fun logD(source:Any,message:String){
    val name=source::class.simpleName
    val output="$name:$message"
    log(TAG,output,false)
}
fun logE(source:Any,message:String){
    val name=source::class.simpleName
    val output="$name:$message"
    log(TAG,output,true)
}
fun log(source:Any,message:String,error:Boolean){
    val name=source::class.simpleName

    val emoji=if (error) "⚠️" else "✅️"
    val output="$emoji - $name:$message"
    if (error){
        Log.e(TAG,message)
    }
    else{
        Log.d(TAG,output)}
}