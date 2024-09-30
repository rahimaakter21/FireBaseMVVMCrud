package com.example.firebasemvvm

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasemvvm.adapter.DataAdapter
import com.example.firebasemvvm.databinding.ActivityMainBinding
import com.example.firebasemvvm.model.Data
import com.example.firebasemvvm.viewmodel.DataViewModel
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityMainBinding
    private  lateinit var adapter: DataAdapter
    private  val dataViewModel: DataViewModel by viewModels()
    private  fun clearInputFields(){
        binding.idEtxt.text.clear()
        binding.nameEtxt.text.clear()
        binding.emailEtxt.text.clear()
        binding.subjectETxt.text.clear()
        binding.codeEtxt.text.clear()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = DataAdapter(listOf(), this)
        binding.recylerView.adapter = adapter
        binding.recylerView.layoutManager = LinearLayoutManager(this)
        dataViewModel.dataList.observe(this, Observer {

            adapter.updateData(it)
        })
        binding.saveBtn.setOnClickListener {
            val studid = binding.idEtxt.text.toString()
            val name = binding.nameEtxt.text.toString()
            val email = binding.emailEtxt.text.toString()
            val subject = binding.subjectETxt.text.toString()
            val subjectcode = binding.codeEtxt.text.toString()
            if (studid.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && subjectcode.isNotEmpty()) {

                val data = Data(null, studid, name, email, subject, subjectcode, Timestamp.now())
                dataViewModel.addData(data, onSuccess = {
                    clearInputFields()
                    Toast.makeText(this@MainActivity, "Data added successfully", Toast.LENGTH_SHORT)
                        .show()

                }, onFailure = {
                    Toast.makeText(this@MainActivity, "Failed to add data", Toast.LENGTH_SHORT)
                        .show()
                }
           )}
        }


    }
         fun onDeleteItemClick(data: Data) {
        AlertDialog.Builder(this).apply {

            setTitle("Delete Data")
            setMessage("Are you sure you want to delete this data?")
            setPositiveButton("Yes") { _, _ ->
                dataViewModel.deleteData(data,
                    onSuccess = {Toast.makeText(this@MainActivity,"data deleted", Toast.LENGTH_SHORT).show()},
                    onFailure = {Toast.makeText(this@MainActivity,"Failed to delete data",Toast.LENGTH_SHORT).show()}
                )
            }
            setNegativeButton("No", null)

        }.show()

    }


        fun onEditItemClick(data: Data) {
            binding.idEtxt.setText(data.studid)
            binding.nameEtxt.setText(data.name)
            binding.emailEtxt.setText(data.email)
            binding.subjectETxt.setText(data.subject)
            binding.codeEtxt.setText(data.subjectcode)
            binding.saveBtn.setOnClickListener {
                val updateData = Data(
                    data.id,
                    binding.idEtxt.text.toString(),
                    binding.nameEtxt.text.toString(),
                    binding.emailEtxt.text.toString(),
                    binding.subjectETxt.text.toString(),
                    binding.codeEtxt.text.toString(),
                    Timestamp.now()
                )

                dataViewModel.updateData(updateData)
                clearInputFields()
                Toast.makeText(this@MainActivity, "Data updated successfully", Toast.LENGTH_SHORT)
                    .show()

            }


        }

}



