package uz.gita.fooddeliveryumidjon.app

import android.app.Application
import uz.gita.fooddeliveryumidjon.data.local.MySharedPreferences

class MyApp : Application() {
    override fun onCreate() {
        MySharedPreferences.init(this)
        super.onCreate()
    }
}