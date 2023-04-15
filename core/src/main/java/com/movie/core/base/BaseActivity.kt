package com.movie.core.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.movie.core.dialog.ProgressDialog
import com.movie.network.model.ErrorModel
import es.dmoral.toasty.Toasty

abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), BaseView {

    open lateinit var binding: V
    open lateinit var viewModel: VM

    lateinit var progressDialog: ProgressDialog


    abstract fun initBinding(): V

    abstract fun initViewModel()

    abstract fun onActivityCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        binding = initBinding()
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        viewModel.errorLiveData.observe(this, this::onError)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        onActivityCreated()
    }

    override fun onError(error: ErrorModel) {
        hideLoading()
        showErrorMsg(error.toString())
    }

    override fun showLoading() {
        progressDialog.show()
    }

    override fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun showSuccessMsg(msg: String) {
        Toasty.success(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun showErrorMsg(msg: String) {
        Toasty.error(this, msg, Toast.LENGTH_LONG).show()
    }

    fun returnError(error: String): Boolean {
        showErrorMsg(error)
        return false
    }

    fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyboard() {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}