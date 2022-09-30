package com.devmasterteam.tasks.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.google.gson.Gson

open class BaseRepository(val context: Context) {



    fun failResponse(str: String): String{
        return Gson().fromJson(str, String::class.java)
    }


    fun isConnerctionAvailable() : Boolean{
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNet = cm.activeNetwork ?: return false
            val netWorkCapabilities =  cm.getNetworkCapabilities(activeNet) ?: return false
            result = when {
                netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false

            }
        }else{
            if (cm.activeNetworkInfo != null){
                result = when(cm.activeNetworkInfo!!.type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }




        return result

    }

}