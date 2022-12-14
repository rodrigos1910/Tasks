package com.devmasterteam.tasks.viewmodel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.ApiListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.ValidationModel
import com.devmasterteam.tasks.service.repository.PersonRepository
import com.devmasterteam.tasks.service.repository.PriorityRepository
import com.devmasterteam.tasks.service.repository.SecurityPreferences
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val  personRepository = PersonRepository(application.applicationContext)
    private val  priorityRepository = PriorityRepository(application.applicationContext)

    private val securityPreferences = SecurityPreferences(application.applicationContext)

    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login



    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {


            personRepository.login(email, password, object :ApiListener<PersonModel> {
                override fun onSucess(result: PersonModel) {

                    securityPreferences.store(TaskConstants.SHARED.TOKEN_KEY, result.token)
                    securityPreferences.store(TaskConstants.SHARED.PERSON_NAME, result.name)
                    securityPreferences.store(TaskConstants.SHARED.PERSON_KEY, result.personKey)

                    RetrofitClient.addHeaders(result.token, result.token)

                    _login.value = ValidationModel()
                }

                override fun onFailure(message: String) {
                    //Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
                    _login.value = ValidationModel(message)
                }


            })




    }

    /**
     * Verifica se usu??rio est?? logado
     */
    fun verifyLoggedUser(){

        var logged: Boolean = false

        val token = securityPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val key = securityPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        RetrofitClient.addHeaders(token, key)
        /*
        if (token != "" && key != ""){
            _login.value = ValidationModel()
        }*/
        logged = (token != "" && key != "")
        _loggedUser.value = logged

        if (!logged){

            priorityRepository.list(object : ApiListener<List<PriorityModel>>{
                override fun onSucess(result: List<PriorityModel>) {
                    priorityRepository.save(result)
                }

                override fun onFailure(message: String) {
                    TODO("Not yet implemented")
                }

            })

        }

    }

}