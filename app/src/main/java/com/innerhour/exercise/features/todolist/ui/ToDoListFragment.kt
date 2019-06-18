package com.innerhour.exercise.features.todolist.ui

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.innerhour.exercise.R
import com.innerhour.exercise.core.viewcontainer.BaseFragment
import com.innerhour.exercise.core.views.FastListAdapter
import com.innerhour.exercise.core.views.bind
import com.innerhour.exercise.features.todolist.data.ToDoViewModel
import com.innerhour.exercise.features.todolist.data.entities.Task
import kotlinx.android.synthetic.main.layout_todo_list.view.*
import kotlinx.android.synthetic.main.view_item_todo_task.view.*
import java.util.*

/**
 * Created by Suyash Chavan.
 */

const val RC_SIGN_IN = 9001

class ToDoListFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.layout_todo_list

    lateinit var toDoViewModel: ToDoViewModel

    override fun prepareMandatoryInstances() {
        toDoViewModel = activity?. run {
            ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        if (shouldStartSignIn()) {
            startSignIn()
            return
        }
    }

    private fun shouldStartSignIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser == null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != AppCompatActivity.RESULT_OK)
                startSignIn()
            else loadDataPostSignIn()

        }
    }

    private fun startSignIn(){
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build()))
            .setIsSmartLockEnabled(false)
            .build()

        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun assignListener() {
        root.add_task.setOnClickListener {
            val direction = ToDoListFragmentDirections.actionToDoListFragmentToAddToDoTaskFragment()
            findNavController().navigate(direction)
        }
    }

    override fun cancleListener() {
        (root.recycler_view.adapter as FastListAdapter).stopListening()
    }

    override fun loadData() {
        dismissProgress()
        if(!shouldStartSignIn()) loadDataPostSignIn()
    }

    private fun loadDataPostSignIn() {
        root.recycler_view.bind(toDoViewModel.getTasks(), singleLayout = R.layout.view_item_todo_task){ taskSnapShot, pos ->

            val task = taskSnapShot.toObject(Task::class.java)!!

            this.title.text = task.taskName
            this.complete.isChecked = task.isCompleted

            this.complete.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = true
                toDoViewModel.getTaskById(taskSnapShot.id).set(
                    task
                )
            }

            this.reminder.setOnClickListener {
                val cal = Calendar.getInstance()
                var hourOfDay = 0
                var minuteOfDay = 0

                when(task.hasAReminderSet) {
                    true -> {
                        hourOfDay = task.reminder.substring(0,2).toInt()
                        minuteOfDay = task.reminder.substring(2,4).toInt()
                    }
                    else -> {
                        hourOfDay = cal.get(Calendar.HOUR_OF_DAY)
                        minuteOfDay = cal.get(Calendar.MINUTE)
                    }
                }

                val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    task.reminder = "$hour$minute"
                    task.hasAReminderSet = true
                    toDoViewModel.getTaskById(taskSnapShot.id).set(
                        task
                    )
                }
                TimePickerDialog(activity!!, timeSetListener, hourOfDay , minuteOfDay, false).show()
            }

            this.setOnClickListener {
                val direction = ToDoListFragmentDirections.actionToDoListFragmentToToDoTaskDetails(taskSnapShot.id)
                findNavController().navigate(direction)
            }
        }

        (root.recycler_view.adapter as FastListAdapter).startListening()
    }

}