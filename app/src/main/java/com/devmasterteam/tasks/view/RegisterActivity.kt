package com.devmasterteam.tasks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.databinding.ActivityLoginBinding
import com.devmasterteam.tasks.databinding.ActivityRegisterBinding
import com.devmasterteam.tasks.viewmodel.LoginViewModel
import com.devmasterteam.tasks.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variáveis da classe
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        // Eventos
        binding.buttonSave.setOnClickListener(this)


        observe()
        // Layout
        setContentView(binding.root)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.button_save -> {
                handleSave()

            }
        }
    }

    fun observe(){
        viewModel.personSave.observe(this){
            if (it.status()){
                Toast.makeText(applicationContext, applicationContext.getString(R.string.register_created),
                    Toast.LENGTH_SHORT ).show()
                finish()
            }else{
                Toast.makeText(applicationContext, it.message(), Toast.LENGTH_SHORT ).show()
            }
        }

    }

    fun handleSave(){
        val nome : String = binding.editName.text.toString()
        val email : String = binding.editEmail.text.toString()
        val senha : String = binding.editPassword.text.toString()

        viewModel.create(nome,email,senha)
    }
}