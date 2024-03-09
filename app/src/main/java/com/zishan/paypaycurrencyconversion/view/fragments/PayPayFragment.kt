package com.zishan.paypaycurrencyconversion.view.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zishan.paypaycurrencyconversion.MainApplication
import com.zishan.paypaycurrencyconversion.databinding.FragmentPayPayBinding
import com.zishan.paypaycurrencyconversion.di.component.DaggerPayPayComponent
import com.zishan.paypaycurrencyconversion.di.factory.ViewModelProviderFactory
import com.zishan.paypaycurrencyconversion.view.adapter.CurrencyExchangeAdapter
import com.zishan.paypaycurrencyconversion.view.uimodel.ExchangeRateUIModel
import com.zishan.paypaycurrencyconversion.view.viewmodel.PayPayViewModel
import javax.inject.Inject

class PayPayFragment : Fragment() {

    private lateinit var fragmentPayPayBinding: FragmentPayPayBinding

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var payPayViewModel: PayPayViewModel
    private var currencySpinnerAdapter: ArrayAdapter<String>? = null
    private val currencyExchangeAdapter: CurrencyExchangeAdapter by lazy {
        CurrencyExchangeAdapter()
    }
    private val currencyRecyclerView: RecyclerView by lazy {
        fragmentPayPayBinding.currencyRecyclerView.apply {
            this.layoutManager = GridLayoutManager(this.context, 3)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentPayPayBinding = FragmentPayPayBinding.inflate(inflater, container, false)
        return fragmentPayPayBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInjection()
        initViewModel()
        setupUI()
        setUpObserver()
    }

    private fun initViewModel() {
        payPayViewModel =
            ViewModelProvider(viewModelStore, viewModelProviderFactory)[PayPayViewModel::class.java]
    }

    private fun setupUI() {
        context?.let {
            currencySpinnerAdapter =
                ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, mutableListOf())
            currencySpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            fragmentPayPayBinding.currencySpinner.adapter = currencySpinnerAdapter

            fragmentPayPayBinding.currencySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        payPayViewModel.selectedSpinnerIndex = position
                        fetchExchangeRateData(fragmentPayPayBinding.amountEditText.text.toString())
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
        }

        fragmentPayPayBinding.amountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                fetchExchangeRateData(s.toString())
            }
        })

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        currencyRecyclerView.adapter = currencyExchangeAdapter
    }

    private fun setUpObserver() {
        payPayViewModel.currencyListLiveData.observe(viewLifecycleOwner) {
            when {
                it.isSuccess -> {
                    currencySpinnerAdapter?.run {
                        clear()
                        addAll((it.getOrNull() as List<String>).toMutableList())
                    }
                }
            }
        }

        payPayViewModel.currencyExchangeLiveData.observe(viewLifecycleOwner) {
            currencyExchangeAdapter.submitList(it)
        }
    }


    private fun initInjection() {
        activity?.application?.let { application ->
            DaggerPayPayComponent.builder()
                .baseAppComponent((application as MainApplication).getBaseComponent()).build()
                .inject(this)
        }
    }

    private fun fetchExchangeRateData(textValue: String) {
        textValue.toDoubleOrNull()?.let {
            payPayViewModel.fetchExchangeRate(it)
        }
    }

}