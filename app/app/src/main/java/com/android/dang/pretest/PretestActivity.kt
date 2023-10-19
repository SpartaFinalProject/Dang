package com.android.dang.pretest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.dang.MainActivity
import com.android.dang.common.Constants
import com.android.dang.databinding.ActivityPretestBinding
import com.android.dang.util.SharedPref

class PretestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPretestBinding
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPref.getBoolean(Constants.IS_PRE_CONFIRM, false)) {
            gotoMainActivity()
            return
        }
        binding = ActivityPretestBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initEvent()
    }

    private fun initEvent() {
        binding.btnYes.setOnClickListener {
            nextQuestion()
        }
        binding.btnNo.setOnClickListener {
            showCustomDialog(isYesDialog = false) {
                index = -1
                switchIntro(true)
                nextQuestion()
            }
        }
        binding.btnStart.setOnClickListener {
            switchIntro(false)
            nextQuestion()
        }
        binding.btnSkip.setOnClickListener {
            gotoMainActivity()
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun nextQuestion() {
        when (++index) {
            in 1..7 -> {
                val resourceId = resources.getIdentifier("pretest_$index", "string", packageName)
                if (resourceId != 0) {
                    binding.tvQuestion.setText(resourceId)
                }
            }
            8 -> {
                showCustomDialog(isYesDialog = true) {
                    SharedPref.setBoolean(Constants.IS_PRE_CONFIRM, true)
                    gotoMainActivity()
                }
                index = 7
            }

            else -> {}
        }

        var numStr = "Q${index}."
        binding.tvQusetionNumber.text = numStr
    }

    private fun showCustomDialog(isYesDialog: Boolean, onFunc: () -> Unit = {}) {
        val customDialog = PretestDialog(this, isYesDialog) { onFunc() }
        customDialog.show()
    }

    private fun switchIntro(isIntroVisible: Boolean) {
        binding.layoutPretest.visibility = if (isIntroVisible) View.GONE else View.VISIBLE
        binding.layoutPretestIntro.visibility = if (isIntroVisible) View.VISIBLE else View.GONE
    }

    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}