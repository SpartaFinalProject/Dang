package com.android.dang.pretest

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.android.dang.R
import com.android.dang.databinding.DialogPretestBinding

class PretestDialog(private val activity: AppCompatActivity, private var isYesDialog: Boolean, private var listener: OnClickListener) :
    Dialog(activity, R.style.RoundedDialogTheme) {

    private lateinit var binding: DialogPretestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPretestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isYesDialog) {
            binding.tvQuestion.setText(R.string.pretest_pass)
            binding.btnYesDialog.visibility = View.VISIBLE
            binding.btnNoDialogContinue.visibility = View.GONE

            binding.btnYesDialog.setOnClickListener {
                listener.onClick(it)
                dismiss()
            }
        } else {
            binding.tvQuestion.setText(R.string.pretest_fail)
            binding.btnYesDialog.visibility = View.GONE
            binding.btnNoDialogContinue.visibility = View.VISIBLE

            binding.btnNoDialogBack.setOnClickListener {
                listener.onClick(it)
                dismiss()
            }

            binding.btnNoDialogContinue.setOnClickListener {
                dismiss()
            }
        }
    }
}