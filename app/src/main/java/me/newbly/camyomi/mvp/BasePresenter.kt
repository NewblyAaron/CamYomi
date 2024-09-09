package me.newbly.camyomi.mvp

import android.view.View

abstract class BasePresenter<V: View> {
    abstract fun start()
    abstract fun stop()
}