package ru.bulat.mukhutdinov.mvvm.common.ui

interface BaseViewModel<V : BaseView> {

    var view: V
}