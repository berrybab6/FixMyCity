package mish.mish.assefa.com.fixmycity.Retrofit

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract
import java.net.PasswordAuthentication

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

class SessionManagement(context: Context) {
     val EMAIL="email"
     val SESSION_KEY="token"
     val ID="_id"
     val SHARED_PREF_NAME="session"

    private var  sharedPreferences:SharedPreferences
     private var editor:SharedPreferences.Editor

   // const val EMAIL="email"
    //const val SESSION_KEY="session_user"
    /*lateinit var  name
    lateinit var lName
    lateinit var email
    lateinit var password
    lateinit var */

    init {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()

    }
    fun saveSession(user:LoginResult){
        val token=user.token
        val email:String=user.email
        val _id:String=user._id
        editor.putString(ID,_id).apply()
        editor.putString(EMAIL,email).apply()
        editor.putString(SESSION_KEY,token).commit()

    }
    fun getSession():String{
        return sharedPreferences.getString(SESSION_KEY,"")
    }
    fun removeSession(){
        editor.putString(SESSION_KEY,"").commit()
    }
    fun getEmail():String{
        return sharedPreferences.getString(EMAIL,"")
    }
}