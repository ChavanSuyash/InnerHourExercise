package com.innerhour.exercise.features.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.innerhour.exercise.R
import com.innerhour.exercise.core.dagger.ViewModelFactory
import com.innerhour.exercise.features.todolist.data.ToDoViewModel
import com.innerhour.exercise.features.todolist.data.dagger.inject
import javax.inject.Inject

/**
 * Created by Suyash Chavan.
 */
class TodoListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        inject()

        ViewModelProviders.of(this, viewModelFactory).get(ToDoViewModel::class.java)
    }
}