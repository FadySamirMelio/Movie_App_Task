package com.movie.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.movie.core.dialog.ProgressDialog
import com.movie.network.model.ErrorModel
import es.dmoral.toasty.Toasty

abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel>
    : Fragment(), BaseView {

    private var _binding: V? = null
    val binding get() = _binding!!

    open lateinit var viewModel: VM

    open lateinit var progressDialog: ProgressDialog

    abstract fun initBinding(): V

    abstract fun initViewModel()

    abstract fun onFragmentCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreate(savedInstanceState)

        initViewModel()
        _binding = initBinding()

        if (context != null) progressDialog = ProgressDialog(requireContext())

        viewModel.errorLiveData.observe(viewLifecycleOwner, this::onError)

        onFragmentCreated()

        return binding.root
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

    override fun onDestroyView() {
        hideLoading()
        _binding = null
        super.onDestroyView()
    }

    fun returnError(error: String): Boolean {
        showErrorMsg(error)
        return false
    }

    override fun showSuccessMsg(msg: String) {
        if (context != null)
            Toasty.success(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    override fun showErrorMsg(msg: String) {
        if (context != null)
            Toasty.error(requireContext(), msg, Toast.LENGTH_LONG).show()
    }


    fun hideKeyboard() {
        try {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}