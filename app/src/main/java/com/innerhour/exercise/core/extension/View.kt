package com.innerhour.exercise.core.extension

import android.app.Service
import android.view.View
import android.view.ViewStub
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Suyash Chavan.
 */
fun ViewStub?.ifNotInflated(inflate: () -> Unit, elseDo: () -> Unit) {
    this?. let { inflate.invoke() } ?: run { elseDo.invoke() }
}

fun View.loadFromUrl(url: String?, isCircular: Boolean = false) {
    if (isCircular) {
        Glide.with(this.context.applicationContext)
            .load(url)
            .apply(RequestOptions().transform(CircleCrop()))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this as ImageView)
    } else {
        Glide.with(this.context.applicationContext)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this as ImageView)
    }
}

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}