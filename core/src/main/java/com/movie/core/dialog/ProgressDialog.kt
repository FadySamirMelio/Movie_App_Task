package com.movie.core.dialog

import android.content.Context
import android.view.WindowManager
import com.movie.core.base.BaseDialog
import com.movie.core.databinding.DialogProgressLayoutBinding

class ProgressDialog(context: Context) : BaseDialog<DialogProgressLayoutBinding>(context) {

    override fun initBinding(): DialogProgressLayoutBinding {
        return DialogProgressLayoutBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {
        setCancelable(false)
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }
}