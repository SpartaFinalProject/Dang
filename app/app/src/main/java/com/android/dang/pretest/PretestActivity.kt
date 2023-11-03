package com.android.dang.pretest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.android.dang.MainActivity
import com.android.dang.common.Constants
import com.android.dang.databinding.ActivityPretestBinding
import com.android.dang.util.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PretestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPretestBinding
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 마지막 pretest 완료 시간 가져오기
        val lastCompletionTime = SharedPref.getLong(Constants.PRETEST_COMPLETION_TIME, 0)
        if (System.currentTimeMillis() - lastCompletionTime < 30 * 24 * 60 * 60 * 1000) {
            // 30일이 지나지 않았으므로 pretest 건너뛰기
            gotoMainActivity()
            return
        }
        binding = ActivityPretestBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initEvent()
    }

    private fun initEvent() {
        binding.tvIntroQuestion.movementMethod = ScrollingMovementMethod.getInstance()
        binding.btnYes.setOnClickListener {
            if (index == 3) {
                showCustomDialog(isYesDialog = false) {
                    index = -1
                    switchIntro(true)
                    nextQuestion()
                }
            } else {
                nextQuestion()
            }
        }
        binding.btnNo.setOnClickListener {
            if (index == 3) {
                nextQuestion()
            } else {
                showCustomDialog(isYesDialog = false) {
                    index = -1
                    switchIntro(true)
                    nextQuestion()
                }
            }
        }



        binding.btnStart.setOnClickListener {
            startPretest()
        }
        binding.btnSkip.setOnClickListener {
            gotoMainActivity()
        }
    }
    private fun startPretest() {
        // 시작 버튼 클릭 시 실행할 코드를 코루틴에서 처리
        CoroutineScope(Dispatchers.Default).launch {
            // 메인 스레드에서 UI 업데이트
            withContext(Main) {
                binding.layoutIntroQuestion.visibility = View.GONE
                binding.avStartTest.visibility = View.VISIBLE
            }

            // 모의 로딩 시간 (예: 2초 대기)
            delay(2000)

            // 메인 스레드에서 다시 UI 업데이트
            withContext(Main) {
                binding.avStartTest.visibility = View.GONE
                binding.layoutIntroQuestion.visibility = View.VISIBLE
                switchIntro(false)
                nextQuestion()
            }
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
                    SharedPref.setLong(Constants.PRETEST_COMPLETION_TIME, System.currentTimeMillis())
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
        binding.tvTestTitle.visibility = if (isIntroVisible) View.GONE else View.VISIBLE
        binding.layoutPretest.visibility = if (isIntroVisible) View.GONE else View.VISIBLE
        binding.layoutPretestIntro.visibility = if (isIntroVisible) View.VISIBLE else View.GONE
    }

    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}