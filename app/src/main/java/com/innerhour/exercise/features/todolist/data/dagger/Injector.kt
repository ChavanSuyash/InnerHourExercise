package com.innerhour.exercise.features.todolist.data.dagger

import com.innerhour.exercise.features.todolist.TodoListActivity
import com.innerhour.exercise.features.todolist.data.dagger.component.DaggerToDoDataComponent

/**
 * Created by Suyash Chavan.
 */
fun TodoListActivity.inject() {
    DaggerToDoDataComponent.builder()
        .build()
        .inject(this)
}