package com.example.firebasemvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasemvvm.DataRepository
import com.example.firebasemvvm.model.Data

class DataViewModel: ViewModel() {
    private  val dataRepository = DataRepository()
    private   var _datalist:MutableLiveData<List<Data>> = dataRepository.fetchData()
    val dataList: LiveData<List<Data>>
    get() = _datalist

fun addData(data: Data,onSuccess: () -> Unit, onFailure: () -> Unit) {
    dataRepository.addData(data)

        .addOnSuccessListener {onSuccess()}
        .addOnFailureListener {onFailure()}
    }
    fun updateData(data:Data){
        dataRepository.updateData(data)

    }

    fun deleteData(data: Data,onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataRepository.deleteData(data)

            .addOnSuccessListener {onSuccess()}
            .addOnFailureListener {onFailure()}
    }

}


