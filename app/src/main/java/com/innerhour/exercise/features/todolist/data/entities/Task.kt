package com.innerhour.exercise.features.todolist.data.entities

import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Suyash Chavan.
 */
@IgnoreExtraProperties
data class Task (
    val taskName: String = "",
    var isCompleted: Boolean = false,
    var hasAReminderSet: Boolean = false,
    var reminder: String = ""
)