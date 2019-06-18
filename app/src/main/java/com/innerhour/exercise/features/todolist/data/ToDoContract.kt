package com.innerhour.exercise.features.todolist.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.innerhour.exercise.features.todolist.data.entities.Comment
import com.innerhour.exercise.features.todolist.data.entities.Task

/**
 * Created by Suyash Chavan.
 */
class ToDoContract {

    interface ToDoRemoteDataSource {
        fun addToDoTask(
            task: Task
        )

        fun getToDoTasks(): CollectionReference

        fun getTaskById(id: String): DocumentReference

        fun getTaskComments(taskReference: DocumentReference): CollectionReference

        fun addACommentToTask(
            taskRef: DocumentReference,
            comment: Comment
        ): com.google.android.gms.tasks.Task<Void>
    }

    interface ToDoRepository {
        fun addToDoTask(
            task: Task
        )

        fun getToDoTasks(): CollectionReference

        fun getTaskById(id: String): DocumentReference

        fun getTaskComments(taskReference: DocumentReference): CollectionReference

        fun addACommentToTask(
            taskRef: DocumentReference,
            comment: Comment
        ): com.google.android.gms.tasks.Task<Void>
    }
}