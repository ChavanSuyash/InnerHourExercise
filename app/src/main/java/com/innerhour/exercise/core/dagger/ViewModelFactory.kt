package com.innerhour.exercise.core.dagger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.innerhour.exercise.core.dagger.scope.ActivityScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Suyash Chavan.
 */
/**
 * Created by Suyash Chavan.
 */
@ActivityScope
class ViewModelFactory @Inject constructor(private val creators: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<ViewModel>? = creators[modelClass]
        Log.e("BeforeNullCheck", "${creator}")
        if (creator == null) {
            for ((key, value) in creators) {
                Log.e("$key****","$value****")
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        Log.e("Creator","${creator}")
        if (creator == null) throw IllegalArgumentException("unknown model class " + modelClass)
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}
