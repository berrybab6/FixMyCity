package mish.mish.assefa.com.fixmycity.user.data.controller

import android.content.Context
import android.content.SharedPreferences
import mish.mish.assefa.com.fixmycity.LoginActivity
import android.content.Intent


class SessionClass (var context:Context) {
    var _context: Context
    var prefs: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE: Int = 0

    /** Sharedpref file name*/
    private val PREF_NAME: String = "AndroidHivePref"
    private val IS_LOGIN = "isLoggedIn"

    val KEY_NAME = "first_name"
    val KEY_EMAIL = "userEmail"
    val KEY_SESSION = "session"
    val KEY_ID = "id"
    val KEY_LNAME="last_name"
    val KEY_PASSWORD="password"
    val KEY_USERNAME="username"
    init {
        this._context = context
        prefs = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = prefs.edit()

    }

    /**
     * Create login session
     */
    fun createLoginSession(name: String,last_name:String, email: String, id: String, token: String,password:String,username:String) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)
        // Storing name in pref
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_LNAME,last_name)
        // Storing email in pref
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_USERNAME,username)
        //storing token in pref
        editor.putString(KEY_SESSION, token)
        editor.putString(KEY_PASSWORD,password)

        //storing user id in pref
        editor.putString(KEY_ID, id)
        // commit changes
        editor.commit()
    }

    /**
     * Get stored session data
     */
    fun getUserDetails(): HashMap<String, String> {
        val user= HashMap<String, String>()
        // user name
        user[KEY_NAME] = prefs.getString(KEY_NAME, "")!!
        // user email id
        user[KEY_EMAIL] = prefs.getString(KEY_EMAIL, "")!!
        // user token
        user[KEY_SESSION] = prefs.getString(KEY_SESSION, "")!!
        // user  id
        user[KEY_ID] = prefs.getString(KEY_ID, "")!!
        user[KEY_PASSWORD]=prefs.getString(KEY_PASSWORD,"")!!
        user[KEY_USERNAME]=prefs.getString(KEY_USERNAME,"")!!
        user[KEY_LNAME]=prefs.getString(KEY_LNAME,"")!!
        // return user
        return user
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    fun checkLogin() {
        if (!this.isLoggedIn()) {
    // user is not logged in redirect him to Login Activity
            val i = Intent(_context, LoginActivity::class.java)
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
       // Staring Login Activity
            _context.startActivity(i)
        }

    }
    /**
     * Clear session details
     */
    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear()
        editor.commit()
        // After logout redirect user to Loing Activity
        val i = Intent(_context, LoginActivity::class.java)
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Add new Flag to start new Activity
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        // Staring Login Activity
        _context.startActivity(i)
    }
}