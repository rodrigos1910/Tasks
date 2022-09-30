package com.devmasterteam.tasks.service.repository.remote

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.repository.BaseRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(context: Context) : BaseRepository(context) {

    //retofit
    private val remote = RetrofitClient.getService(TaskService::class.java)


    fun load(id: Int, listener: ApiListener<TaskModel>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call =  remote.load(id)
        call.enqueue(object : Callback<TaskModel> {
            override fun onResponse(call: Call<TaskModel>, response: Response<TaskModel>) {

                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<TaskModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })


    }

    fun list( listener: ApiListener<List<TaskModel>>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call =  remote.list()
        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(call: Call<List<TaskModel>>, response: Response<List<TaskModel>>) {

                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })


    }

    fun listNext( listener: ApiListener<List<TaskModel>>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.listNext()
        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(call: Call<List<TaskModel>>, response: Response<List<TaskModel>>) {

                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })


    }

    fun listOverduo( listener: ApiListener<List<TaskModel>>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call =  remote.listOverduo()
        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(call: Call<List<TaskModel>>, response: Response<List<TaskModel>>) {

                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })


    }

    fun create(task:TaskModel, listener: ApiListener<Boolean>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.create(
            task.priorityId, task.description, task.dueDate, task.complete

        )
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }


        })


    }

    fun update(task:TaskModel, listener: ApiListener<Boolean>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.update(
            task.id, task.priorityId, task.description, task.dueDate, task.complete

        )
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }


        })


    }

    fun delete (id: Int,listener: ApiListener<Boolean>){

        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.delete(id)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }


        })
    }

    fun undoTask(id: Int,listener: ApiListener<Boolean>){
        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.undo(id)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }


        })
    }

    fun completeTask(id: Int, listener: ApiListener<Boolean>){
        if (!isConnerctionAvailable()){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }
        val call =  remote.complete(id)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.code() == TaskConstants.HTTP.SUCCESS){
                    response.body()?.let { listener.onSucess(it) }

                }else{
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }


        })
    }






}