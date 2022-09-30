package com.devmasterteam.tasks.service.repository

import android.content.ClipDescription
import android.content.Context
import android.widget.Toast
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.repository.local.TaskDatabase
import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.PriorityService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(context: Context) : BaseRepository(context) {


    //retofit
    private val remote = RetrofitClient.getService(PriorityService::class.java)
    //database
    private val database = TaskDatabase.getDatabase(context.applicationContext).priorityDAO()

    fun list( listener: ApiListener<List<PriorityModel>>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call =  remote.list()
        call.enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(call: Call<List<PriorityModel>>, response: Response<List<PriorityModel>>) {

                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })


    }

    fun save(list: List<PriorityModel>) {

        database.clear()
        database.save(list)
    }



    fun list() :  List<PriorityModel> {
        return database.list()
    }



    //cache para evitar consumo no banco
    companion object{
        private val cache = mutableMapOf<Int ,String>()
        fun getDescription(id: Int): String{
            return cache[id] ?: ""
        }
        fun setDescription(id: Int, description: String){
            cache[id] = description
        }

    }

    fun getDescription(id: Int) : String{

        val cached =  PriorityRepository.getDescription(id)

        if (cached == ""){
            val description = database.getDescription(id)
            setDescription(id,description )
            return description
        }else{
            return cached
        }


    }
}