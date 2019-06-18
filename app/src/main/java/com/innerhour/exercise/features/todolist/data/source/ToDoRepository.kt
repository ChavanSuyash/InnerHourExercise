package com.innerhour.exercise.features.todolist.data.source

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.innerhour.exercise.features.todolist.data.ToDoContract
import com.innerhour.exercise.features.todolist.data.entities.Comment
import com.innerhour.exercise.features.todolist.data.entities.Task

/**
 * Created by Suyash Chavan.
 */
class ToDoRepository (
    private val todoRemoteDataSource: ToDoRemoteDataSource
): ToDoContract.ToDoRepository {

    override fun getToDoTasks(): CollectionReference {
        return todoRemoteDataSource.getToDoTasks()
    }

    override fun getTaskById(id: String): DocumentReference {
        return todoRemoteDataSource.getTaskById(id)
    }

    override fun addToDoTask(task: Task) {
        todoRemoteDataSource.addToDoTask(task)
    }

    override fun getTaskComments(taskReference: DocumentReference): CollectionReference {
        return todoRemoteDataSource.getTaskComments(taskReference)
    }

    override fun addACommentToTask(
        taskRef: DocumentReference,
        comment: Comment
    ): com.google.android.gms.tasks.Task<Void> {
        return todoRemoteDataSource.addACommentToTask(
            taskRef,
            comment
        )
    }

}