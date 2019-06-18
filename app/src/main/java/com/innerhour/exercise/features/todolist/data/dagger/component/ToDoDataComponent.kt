package com.innerhour.exercise.features.todolist.data.dagger.component

import com.innerhour.exercise.core.dagger.scope.ActivityScope
import com.innerhour.exercise.features.todolist.TodoListActivity
import com.innerhour.exercise.features.todolist.data.dagger.module.ToDoDataModule
import com.innerhour.exercise.features.todolist.data.dagger.module.ToDoViewModelModule
import dagger.Component

/**
 * Created by Suyash Chavan.
 */
@Component(
    modules = [
        ToDoDataModule::class,
        ToDoViewModelModule::class
    ]
)
@ActivityScope
interface ToDoDataComponent {

    @Component.Builder interface Builder {
        fun build() : ToDoDataComponent
    }

    fun inject(todoListActivity: TodoListActivity)
}