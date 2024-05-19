package com.dubitaxi.ui.selectUserType.language

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dubitaxi.R
import com.dubitaxi.databinding.ActivityLanguageBinding
import com.dubitaxi.ui.SplashScreen.WalkthroughActivity
import com.dubitaxi.utils.statusBarTransparent

class LanguageActivity : AppCompatActivity(), View.OnClickListener {
    var selectedLanguage: String? = null//BaseActivity() , View.OnClickListener {
    lateinit var binding: ActivityLanguageBinding
    val name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()
        initViews()
        initControll()
    }

    private fun initControll() {
        binding.constHeaderP.setOnClickListener(this)
        binding.constEnglishLanP.setOnClickListener(this)
        binding.constSpanishP.setOnClickListener(this)
        binding.btnLanguageNextP.setOnClickListener(this)
    }

    private fun initViews() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.const_HeaderP -> {
                binding.constLanguageSelectionP.visibility = View.VISIBLE
            }

            R.id.const_English_lanP -> {
                binding.mSelectLanguage.visibility = View.GONE
                binding.imgLanguageFlagPP.visibility = View.VISIBLE
                binding.tvLanguageNameP.visibility = View.VISIBLE
                binding.imgLanguageEngCheckBoxP.visibility = View.VISIBLE
                binding.imgLanguageSpanishCheckBoxP.visibility = View.GONE
                binding.constLanguageSelectionP.visibility = View.GONE
                binding.imgLanguageFlagPP.setImageDrawable(resources.getDrawable(R.drawable.language_english))
                binding.tvLanguageNameP.text = "English"
                selectedLanguage = "English"
            }

            R.id.const_SpanishP -> {
                binding.mSelectLanguage.visibility = View.GONE
                binding.imgLanguageFlagPP.visibility = View.VISIBLE
                binding.tvLanguageNameP.visibility = View.VISIBLE
                binding.imgLanguageEngCheckBoxP.visibility = View.GONE
                binding.imgLanguageSpanishCheckBoxP.visibility = View.VISIBLE
                binding.constLanguageSelectionP.visibility = View.GONE
                binding.imgLanguageFlagPP.setImageDrawable(resources.getDrawable(R.drawable.language_spanish))
                binding.tvLanguageNameP.text = "Spanish"
                selectedLanguage = "Spanish"
            }

            R.id.btn_language_nextP -> {
                if (selectedLanguage != null) {
                    startActivity(Intent(this, WalkthroughActivity::class.java))
                } else {
                    Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}