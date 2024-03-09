package com.zishan.paypaycurrencyconversion.view.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zishan.paypaycurrencyconversion.R
import com.zishan.paypaycurrencyconversion.databinding.ActivityPayPayBinding
import com.zishan.paypaycurrencyconversion.view.fragments.PayPayFragment

class PayPayActivity : AppCompatActivity() {

    private val viewBinding: ActivityPayPayBinding by lazy {
        ActivityPayPayBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PayPayFragment(), "PayPayFragment")
            .commit()
    }
}