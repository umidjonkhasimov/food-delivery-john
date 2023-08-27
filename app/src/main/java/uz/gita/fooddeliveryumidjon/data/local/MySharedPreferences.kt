package uz.gita.fooddeliveryumidjon.data.local

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class MySharedPreferences private constructor(context: Context) {
    private val myPrefs: SharedPreferences
    private val editor: Editor

    init {
        myPrefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        editor = myPrefs.edit()
    }

    companion object {
        private val MY_PREFS = "my_prefs"
        private val IS_SIGNED_IN = "is_signed_in"
        private val CURRENT_USER_NAME = "current_user_name"


        private var instance: MySharedPreferences? = null

        fun init(context: Context) {
            if (instance == null)
                instance = MySharedPreferences(context)
        }

        fun getInstance(): MySharedPreferences {
            return instance!!
        }
    }

    var isSignedIn: Boolean
        get() = myPrefs.getBoolean(IS_SIGNED_IN, false)
        set(value) = myPrefs.edit().putBoolean(IS_SIGNED_IN, value).apply()

    var currentUserName: String?
        get() = myPrefs.getString(CURRENT_USER_NAME, null)
        set(value) = myPrefs.edit().putString(CURRENT_USER_NAME, value).apply()

}