package com.todoapplication.domain.models

import java.io.Serializable

data class Task(
    val id: Long,
    val text: String,
    val importance: Importance,
    val executed: Boolean,
    val dateOfCreation: Long,
    val dateOfModified: Long,
    val deadline: Long? = null
) : Serializable
