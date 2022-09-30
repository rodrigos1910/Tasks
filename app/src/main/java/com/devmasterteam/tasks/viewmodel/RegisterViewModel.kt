package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PersonRepository
import com.devmasterteam.tasks.service.repository.PriorityRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val  personRepository = PersonRepository(application.applicationContext)


    private val _personSave = MutableLiveData<ValidationModel>()
    val personSave: LiveData<ValidationModel> = _personSave

    fun create(name: String, email: String, password: String) {
        personRepository.create(name,email,password, object : ApiListener<PersonModel> {

            override fun onSucess(result: PersonModel) {
                _personSave.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _personSave.value = ValidationModel(message)
            }



        })
    }

}