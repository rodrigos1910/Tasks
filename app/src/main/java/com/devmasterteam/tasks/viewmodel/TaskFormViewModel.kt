package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.remote.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val  priorityRepository = PriorityRepository(application.applicationContext)
    private val  taskRepository = TaskRepository(application.applicationContext)

    private val _priorityList = MutableLiveData<List<PriorityModel>>()
    val priorityList: LiveData<List<PriorityModel>> = _priorityList


    private val _taskModel = MutableLiveData<TaskModel>()
    val taskModel: LiveData<TaskModel> = _taskModel

    private val _taskLoad = MutableLiveData<ValidationModel>()
    val taskLoad: LiveData<ValidationModel> = _taskLoad


    private val _taskSave = MutableLiveData<ValidationModel>()
    val taskSave: LiveData<ValidationModel> = _taskSave


    fun loadPriority() {
        _priorityList.value = priorityRepository.list()
    }

    fun load(id: Int) {
        taskRepository.load(id, object :ApiListener<TaskModel>{
            override fun onSucess(result: TaskModel) {
                _taskModel.value = result
                _taskLoad.value = ValidationModel()

            }

            override fun onFailure(message: String) {
                _taskLoad.value = ValidationModel(message)
            }

        })
    }

    fun save(task: TaskModel){
        if (task.id <= 0){
            taskRepository.create(task, object : ApiListener<Boolean>{
                override fun onSucess(result: Boolean) {
                    _taskSave.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    _taskSave.value = ValidationModel(message)
                }

            })
        }else{
            taskRepository.update(task, object : ApiListener<Boolean>{
                override fun onSucess(result: Boolean) {
                    _taskSave.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    _taskSave.value = ValidationModel(message)
                }

            })
        }




    }

}