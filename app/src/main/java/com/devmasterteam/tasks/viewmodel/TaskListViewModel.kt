package com.devmasterteam.tasks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.constants.TaskConstants
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


    private val _taskComplete  =  MutableLiveData<ValidationModel>()
    val taskComplete: LiveData<ValidationModel> = _taskComplete

    private val _taskUndo  =  MutableLiveData<ValidationModel>()
    val taskUndo: LiveData<ValidationModel> = _taskUndo

    private var taskFilter = 0


    fun list(filter: Int){

        taskFilter = filter

        val listener = object :ApiListener<List<TaskModel>>{
            override fun onSucess(result: List<TaskModel>) {
                _taks.value = result
            }

            override fun onFailure(message: String) {

            }

        }


        when (filter){
            TaskConstants.FILTER.ALL ->{
                taskRepository.list(listener)
            }
            TaskConstants.FILTER.NEXT ->{
                taskRepository.listNext(listener)
            }
            TaskConstants.FILTER.EXPIRED ->{
                taskRepository.listOverduo(listener)
            }
        }


    }

    fun delete(id: Int) {
        taskRepository.delete(id, object :ApiListener<Boolean>{
            override fun onSucess(result: Boolean) {
                list(taskFilter)
                _taskDelete.value =  ValidationModel()
            }

            override fun onFailure(message: String) {
                _taskDelete.value =  ValidationModel(message)
            }

        })
    }

    fun undoTask(id: Int){
        taskRepository.undoTask(id, object :ApiListener<Boolean>{
            override fun onSucess(result: Boolean) {
                list(taskFilter)
                _taskComplete.value =  ValidationModel()
            }

            override fun onFailure(message: String) {
                _taskComplete.value =  ValidationModel(message)
            }

        })
    }

    fun completeTask(id: Int){
        taskRepository.completeTask(id, object :ApiListener<Boolean>{
            override fun onSucess(result: Boolean) {
                list(taskFilter)
                _taskUndo.value =  ValidationModel()
            }

            override fun onFailure(message: String) {
                _taskUndo.value =  ValidationModel(message)
            }

        })
    }


}