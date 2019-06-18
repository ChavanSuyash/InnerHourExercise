package com.innerhour.exercise.features.todolist.data

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.innerhour.exercise.features.todolist.data.entities.Comment
import com.innerhour.exercise.features.todolist.data.entities.Task
import com.innerhour.exercise.features.todolist.data.source.ToDoRepository
import javax.inject.Inject

/**
 * Created by Suyash Chavan.
 */
class ToDoViewModel @Inject constructor(
    private val todoRepository: ToDoRepository
): ViewModel() {

    fun addTask(
        task: Task
    ){
        todoRepository.addToDoTask(
            task
        )
    }

    fun getTasks(): CollectionReference {
        return todoRepository.getToDoTasks()
    }

    fun getTaskById(
        id: String
    ): DocumentReference{
        return todoRepository.getTaskById(id)
    }

    fun getTaskComments(
        taskReference: DocumentReference
    ): CollectionReference {
        return todoRepository.getTaskComments(taskReference)
    }

    fun addACommentToTask(
        taskRef: DocumentReference,
        comment: Comment
    ): com.google.android.gms.tasks.Task<Void> {
        return todoRepository.addACommentToTask(
            taskRef,
            comment
        )
    }
}