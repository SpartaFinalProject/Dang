package com.android.dang

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.dang.databinding.ActivityPretestBinding

class PretestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPretestBinding
    private var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPretestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()
    }

    private fun initEvent() {
        binding.btnYes.setOnClickListener {
            nextQuestion()
        }
        binding.btnNo.setOnClickListener {
            val customDialog = PretestDialog(this, isYesDialog = false)
            customDialog.show()
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
                val customDialog = PretestDialog(this, isYesDialog = true)
                customDialog.show()
                index = 7
            }

            else -> {}
        }

        var numStr = "Q${index}."
        binding.tvQusetionNumber.text = numStr
    }
}