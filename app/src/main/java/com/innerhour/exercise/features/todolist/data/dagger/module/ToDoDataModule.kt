package com.innerhour.exercise.features.todolist.data.dagger.module

import com.google.firebase.firestore.FirebaseFirestore
import com.innerhour.exercise.features.todolist.data.source.ToDoRemoteDataSource
import com.innerhour.exercise.features.todolist.data.source.ToDoRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Suyash Chavan.
 */
@Module
class ToDoDataModule  {

    @Provides
    fun providersToDoRepository(
        todoRemoteDataSource: ToDoRemoteDataSource
    ): ToDoRepository{
        return ToDoRepository(todoRemoteDataSource)
    }

    @Provides
    fun providersToDRemoteDataSource(
        firestore: FirebaseFirestore
    ): ToDoRemoteDataSource{
        return ToDoRemoteDataSource(firestore)
    }

    @Provides
    fun providesFirestore():FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}