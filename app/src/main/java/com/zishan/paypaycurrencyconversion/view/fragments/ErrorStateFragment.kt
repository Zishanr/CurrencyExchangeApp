package com.zishan.paypaycurrencyconversion.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zishan.paypaycurrencyconversion.databinding.FragmentErrorStateBinding

class ErrorStateFragment : Fragment(){
    private lateinit var fragmentErrorStateBinding: FragmentErrorStateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentErrorStateBinding = FragmentErrorStateBinding.inflate(inflater, container, false)
        return fragmentErrorStateBinding.root
    }
}
