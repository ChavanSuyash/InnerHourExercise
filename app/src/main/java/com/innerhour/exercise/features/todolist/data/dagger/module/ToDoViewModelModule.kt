package com.innerhour.exercise.features.todolist.data.dagger.module

import androidx.lifecycle.ViewModel
import com.innerhour.exercise.core.dagger.ViewModelKey
import com.innerhour.exercise.features.todolist.data.ToDoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Suyash Chavan.
 */
@Module
abstract class ToDoViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ToDoViewModel::class)
    internal abstract fun toDoViewModel(viewModel: ToDoViewModel): ViewModel

}