package com.movie.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movie.network.model.ErrorModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    inline fun CoroutineScope.launchCatching(
        noinline block: suspend CoroutineScope.() -> Unit,
        crossinline onError: (Throwable) -> Unit,
    ) {
        launch(
            CoroutineExceptionHandler { _, throwable -> onError(throwable) },
            block = block
        )
    }

    val errorLiveData: MutableLiveData<ErrorModel> by lazy {
        MutableLiveData<ErrorModel>()
    }

    fun handleError(t: Throwable) {
        val error: ErrorModel = when (t) {
            is ErrorModel -> {
                t
            }
            is HttpException -> {
                ErrorModel("Network Error")
            }
            is UnknownHostException -> {
                ErrorModel("No Internet")
            }
            else -> {
                ErrorModel("Something Went Wrong ${t.localizedMessage}")
            }
        }
        errorLiveData.postValue(error)
    }
}
