package com.innerhour.exercise.features.todolist.data.source

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.innerhour.exercise.features.todolist.data.ToDoContract
import com.innerhour.exercise.features.todolist.data.entities.Comment
import com.innerhour.exercise.features.todolist.data.entities.Task

/**
 * Created by Suyash Chavan.
 */
class ToDoRemoteDataSource(
    private val firestore: FirebaseFirestore
) : ToDoContract.ToDoRemoteDataSource {

    override fun getToDoTasks(): CollectionReference {
        return firestore.collection(TASKS)
    }

    override fun getTaskById(id: String): DocumentReference {
        return firestore.collection(TASKS).document(id)
    }

    override fun addToDoTask(task: Task) {
        val tasks = firestore.collection(TASKS)
        tasks.add(task)
    }

    override fun getTaskComments(taskReference: DocumentReference): CollectionReference {
        return taskReference.collection(COMMENTS)
    }

    override fun addACommentToTask(
        taskRef: DocumentReference,
        comment: Comment
    ): com.google.android.gms.tasks.Task<Void> {
        val commentRef = taskRef.collection(COMMENTS)
            .document()

        return firestore.runTransaction{ transaction ->
            val task = transaction.get(taskRef)
                .toObject(Task::class.java)!!

            // Commit to Firestore
            transaction.set(taskRef, task)
            transaction.set(commentRef, comment)

            null
        }
    }


    companion object{
        const val TASKS = "tasks"
        const val COMMENTS ="comments"
    }
}