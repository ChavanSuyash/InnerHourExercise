package com.innerhour.exercise.features.todotaskdetails.ui

import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ListenerRegistration
import com.innerhour.exercise.R
import com.innerhour.exercise.core.viewcontainer.BaseFragment
import com.innerhour.exercise.core.views.FastListAdapter
import com.innerhour.exercise.core.views.bind
import com.innerhour.exercise.features.todolist.data.ToDoViewModel
import com.innerhour.exercise.features.todolist.data.entities.Comment
import com.innerhour.exercise.features.todolist.data.entities.Task
import com.innerhour.exercise.features.todotaskdetails.ui.commentdialog.CommentDialog
import kotlinx.android.synthetic.main.layout_todo_task_details.view.*
import kotlinx.android.synthetic.main.view_item_comment.view.*

/**
 * Created by Suyash Chavan.
 */
class ToDoTaskDetails : BaseFragment(), CommentDialog.CommentListener {

    override fun layoutId(): Int = R.layout.layout_todo_task_details

    lateinit var toDoViewModel: ToDoViewModel
    lateinit var taskReference: DocumentReference

    var taskListener: ListenerRegistration? = null

    val idOfTask by lazy {
        ToDoTaskDetailsArgs.fromBundle(arguments!!).id
    }

    override fun prepareMandatoryInstances() {
        toDoViewModel = activity?.run {
            ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun assignListener() {
        taskListener = taskReference.addSnapshotListener{ taskSnapshot, exception ->
            if(exception != null){
                // Show error
                return@addSnapshotListener
            }

            val task = taskSnapshot!!.toObject(Task::class.java)!!
            root.task_title.text = task.taskName
            val dailyReminder = if(task.hasAReminderSet) {
                resources.getString(R.string.label_daily_reminder, "${task.reminder.substring(0,2)}:${task.reminder.substring(2,4)}")
            } else {
                resources.getString(R.string.label_daily_reminder, "Not set")
            }
            root.label_daily_reminder.text = dailyReminder

            root.button_comment.setOnClickListener {
                val commentDialog = CommentDialog()
                commentDialog.setTargetFragment(this, 0)
                commentDialog.show(fragmentManager!!,"CommentDialog")
            }

            root.recycler_view.bind(toDoViewModel.getTaskComments(taskReference), singleLayout = R.layout.view_item_comment){commentSnapshot, pos ->
                if(exception != null){
                    // Show error

                }
                val comment = commentSnapshot.toObject(Comment::class.java)!!
                this.comment.text = comment.commentText
            }
            (root.recycler_view.adapter as FastListAdapter).startListening()
        }
    }

    override fun onComment(comment: Comment) {
        toDoViewModel.addACommentToTask(
            taskReference,
            comment
        ).addOnSuccessListener {
            Log.e("Comment add","Success")
        }.addOnFailureListener {
            Log.e("Comment add","Failure")
        }
    }

    override fun cancleListener() {
        taskListener ?. run {
            this.remove()
        }
    }

    override fun loadData() {
        taskReference = toDoViewModel.getTaskById(idOfTask)
        dismissProgress()
    }

}