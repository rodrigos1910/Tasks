package com.devmasterteam.tasks.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.databinding.ActivityRegisterBinding
import com.devmasterteam.tasks.databinding.ActivityTaskFormBinding
import com.devmasterteam.tasks.service.model.PriorityModel
import com.devmasterteam.tasks.service.model.TaskModel
import com.devmasterteam.tasks.viewmodel.RegisterViewModel
import com.devmasterteam.tasks.viewmodel.TaskFormViewModel
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: TaskFormViewModel
    private lateinit var binding: ActivityTaskFormBinding

    private val dateFormat : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private var listPriority: List<PriorityModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variáveis da classe
        viewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)
        binding = ActivityTaskFormBinding.inflate(layoutInflater)

        // Eventos
        binding.buttonSave.setOnClickListener(this)
        binding.buttonDate.setOnClickListener(this)

        viewModel.loadPriority()

        observe()
        // Layout
        setContentView(binding.root)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button_date ->{
                handleDate()
            }
            R.id.button_save ->{
                handleSave()
            }
        }
    }

    override fun onDateSet(v: DatePicker, year: Int, month: Int, dayofMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month,dayofMonth)

        val dueDate = dateFormat.format(calendar.time)
        binding.buttonDate.text = dueDate
    }

    private fun handleDate(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this,year, month, day).show()

    }

    private fun observe(){
        viewModel.priorityList.observe(this
        ) {
            listPriority = it
            val  listStr = mutableListOf<String>()
            it.forEach {
                listStr.add(it.description)
            }
            //Popular o Spinner
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item , listStr)
            binding.spinnerPriority.adapter = adapter
        }


        viewModel.taskSave.observe(this,){
                if (it.status()){
                    Toast.makeText(applicationContext, applicationContext.getString(R.string.task_created),Toast.LENGTH_SHORT ).show()
                    finish()
                }else{
                    Toast.makeText(applicationContext, it.message(),Toast.LENGTH_SHORT ).show()
                }

        }
    }


    private fun handleSave(){
        val task = TaskModel().apply {
            this.id = 0
            this.description = binding.editDescription.text.toString()
            this.complete = binding.checkComplete.isChecked
            this.dueDate = binding.buttonDate.text.toString()
            val index = binding.spinnerPriority.selectedItemPosition
            this.priorityId = listPriority[index].id
        }

        viewModel.save(task)

    }


}