package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PersonRepository(context: Context)  : BaseRepository(context) {


    private val remote = RetrofitClient.getService(PersonService::class.java)




    fun login(email: String, password: String, listener: ApiListener<PersonModel>) {
        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.login(email,password)
        call.enqueue(object : Callback<PersonModel>{
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {

                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{

                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
               listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }



    fun create(name:String, email: String, password: String, listener: ApiListener<PersonModel>) {
        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.create(name, email,password, false)
        call.enqueue(object : Callback<PersonModel>{
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {

                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{

                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }



}