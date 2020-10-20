package mish.mish.assefa.com.fixmycity.data.municipality

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import mish.mish.assefa.com.fixmycity.LoginActivity
import mish.mish.assefa.com.fixmycity.LoginOtherActivity


class MunicipSession (var context: Context) {
    var _context: Context
    var prefs: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE: Int = 0

    /** Sharedpref file name*/
    private val PREF_NAME: String = "AndroidHivePref"
    private val IS_LOGIN = "isLoggedIn"

    val KEY_NAME = "first_name"
    val KEY_ID = "_id"
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
    fun createMunipSession(name: String,id: String,password:String,username:String) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)
        // Storing name in pref
        editor.putString(KEY_NAME, name)

        editor.putString(KEY_USERNAME,username)
        //storing token in pref

        editor.putString(KEY_PASSWORD,password)

        //storing user id in pref
        editor.putString(KEY_ID, id)
        // commit changes
        editor.commit()
    }

    /**
     * Get stored session data
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getMunipDetails(): HashMap<String, String> {
        val municipality = HashMap<String, String>()
        // user name
        municipality[KEY_NAME] = prefs.getString(KEY_NAME, null)

        // user  id
        municipality[KEY_ID] = prefs.getString(KEY_ID, null)
        municipality[KEY_PASSWORD]=prefs.getString(KEY_PASSWORD,null)
        municipality[KEY_USERNAME]=prefs.getString(KEY_USERNAME,null)

        // return user
        return municipality
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
            val i = Intent(_context, LoginOtherActivity::class.java)
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