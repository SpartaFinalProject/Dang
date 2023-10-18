package com.android.dang

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.dang.databinding.DialogPretestBinding

class PretestDialog(private val activity: AppCompatActivity, var isYesDialog: Boolean) :
    Dialog(activity, R.style.RoundedDialogTheme) {

    private lateinit var binding: DialogPretestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPretestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isYesDialog) {
            binding.tvQuestion.setText(R.string.pretest_pass)
            binding.btnYesDialog.visibility = View.VISIBLE
            binding.btnNoDialog.visibility = View.GONE
        } else {
            binding.tvQuestion.setText(R.string.pretest_fail)
            binding.btnYesDialog.visibility = View.GONE
            binding.btnNoDialog.visibility = View.VISIBLE
        }

        binding.btnNoDialog.setOnClickListener {
            dismiss()
        }

        binding.btnYesDialog.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}