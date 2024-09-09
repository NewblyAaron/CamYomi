package me.newbly.camyomi.mvp

import android.view.View

abstract class BasePresenter<V>(
    protected open val view: V
) {
    abstract fun start()
    abstract fun stop()
}