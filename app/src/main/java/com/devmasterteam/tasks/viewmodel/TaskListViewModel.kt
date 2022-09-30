package com.devmasterteam.tasks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.remote.TaskRepository

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    val taskRepository = TaskRepository(application)


    private val _taks = MutableLiveData<List<TaskModel>>()
    val taks: LiveData<List<TaskModel>> = _taks

    private val _taskDelete  =  MutableLiveData<ValidationModel>()
    val taskDelete: LiveData<ValidationModel> = _taskDelete



    fun list(){
        taskRepository.list(object :ApiListener<List<TaskModel>>{
            override fun onSucess(result: List<TaskModel>) {
               _taks.value = result
            }

            override fun onFailure(message: String) {

            }

        })
    }

    fun delete(id: Int) {
        taskRepository.delete(id, object :ApiListener<Boolean>{
            override fun onSucess(result: Boolean) {
                list()
                _taskDelete.value =  ValidationModel()
            }

            override fun onFailure(message: String) {
                _taskDelete.value =  ValidationModel(message)
            }

        })
    }




}