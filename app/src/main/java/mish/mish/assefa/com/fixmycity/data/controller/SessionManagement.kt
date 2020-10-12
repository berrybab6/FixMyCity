package mish.mish.assefa.com.fixmycity.data.controller

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract
import mish.mish.assefa.com.fixmycity.data.user.User
import java.net.PasswordAuthentication

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

class SessionManagement(context: Context) {
     val EMAIL="email"
     val SESSION_KEY="token"
     val ID="_id"
     val SHARED_PREF_NAME="session"
    val PASSWORD="password"
    val FNAME="first_name"
    val LNAME="last_name"
    val USERNAME="username"
    private var  sharedPreferences:SharedPreferences
     private var editor:SharedPreferences.Editor


    init {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()

    }
    fun saveSession(user:User){
        val token=user.token
        val email:String=user.email
        val _id:String=user._id
        val first_name:String=user.first_name
        val last_name:String=user.last_name
        val username:String=user.username
        val password:String=user.password

        editor.putString(PASSWORD,password).commit()
        editor.putString(LNAME,last_name).commit()
        editor.putString(FNAME,first_name).commit()
        editor.putString(ID,_id).commit()
        editor.putString(EMAIL,email).commit()
        editor.putString(USERNAME,username).commit()
        editor.putString(SESSION_KEY,token).commit()


    }

    fun getId():String{
    return sharedPreferences.getString(ID,"")
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
    fun getUserName():String{
        return sharedPreferences.getString(USERNAME,"")
    }

    fun getLastName():String{
        return sharedPreferences.getString(LNAME,"")
    }
    fun getFirstName():String{
        return sharedPreferences.getString(FNAME,"")

    }
    fun getPassword():String{
        return sharedPreferences.getString(PASSWORD,"")
    }
    fun getUser(): User {
        return User(FNAME,LNAME,PASSWORD,EMAIL,ID,USERNAME,SESSION_KEY)
    }
}