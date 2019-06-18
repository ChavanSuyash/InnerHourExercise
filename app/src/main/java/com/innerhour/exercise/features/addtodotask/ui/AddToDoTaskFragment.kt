package com.innerhour.exercise.features.addtodotask.ui

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.innerhour.exercise.R
import com.innerhour.exercise.core.extension.hideKeyboard
import com.innerhour.exercise.core.viewcontainer.BaseFragment
import com.innerhour.exercise.features.todolist.data.ToDoViewModel
import com.innerhour.exercise.features.todolist.data.entities.Task
import kotlinx.android.synthetic.main.layout_add_task.view.*

/**
 * Created by Suyash Chavan.
 */
class AddToDoTaskFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.layout_add_task

    lateinit var toDoViewModel: ToDoViewModel

    override fun prepareMandatoryInstances() {
        toDoViewModel = activity?.run {
            ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun assignListener() {
        root.add_task.setOnClickListener {
            toDoViewModel.addTask(
                Task(
                    root.add_task_title.text.toString(),
                    false
                )
            )
            root.hideKeyboard()
            findNavController().navigateUp()
        }
    }

    override fun cancleListener() {

    }

    override fun loadData() {
        dismissProgress()
    }
}