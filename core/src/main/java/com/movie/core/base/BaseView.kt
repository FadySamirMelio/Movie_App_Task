package com.movie.core.base

import com.movie.network.model.ErrorModel

interface BaseView {

    fun onError(error: ErrorModel)

    fun showLoading()

    fun hideLoading()

    fun showSuccessMsg(msg: String)

    fun showErrorMsg(msg: String)
}