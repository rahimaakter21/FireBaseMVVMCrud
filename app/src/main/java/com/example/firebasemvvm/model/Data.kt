package com.example.firebasemvvm.model

import com.google.firebase.Timestamp

data class Data(
    var id: String? = null,
    val studid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val subject: String? = null,
    val subjectcode: String? = null,
    val timestamp: Timestamp? = null
)
