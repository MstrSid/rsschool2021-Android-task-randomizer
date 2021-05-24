package com.rsschool.android2021

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.rsschool.android2021.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var communicator: Communicator
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        binding.previousResult.text = "Previous result: ${result.toString()}"

        var min = 0
        var max = 0
        val minValue = binding.minValue
        val maxValue = binding.maxValue
        communicator = activity as Communicator

        setGenerate(false)

        minValue.doAfterTextChanged {
            minValue.text.toString().let {
                when (true) {
                    TextUtils.isEmpty(it) -> {
                        minValue.error = getString(R.string.ERROR_EMPTY)
                        setGenerate(false)
                    }
                    it.toDouble() > 2_147_483_647 ->{
                        minValue.setText("")
                        minValue.error = getString(R.string.ERROR_TOO_BIG)
                        setGenerate(false)
                    }
                    it.toInt() < 0 -> {
                        minValue.error = getString(R.string.ERROR_TOO_SMALL)
                        setGenerate(false)
                    }
                    it.toIntOrNull() == null -> {
                        minValue.error = getString(R.string.ERROR_INVALID_COMMON)
                        setGenerate(false)
                    }
                    !TextUtils.isEmpty(maxValue.text.toString()) && it.toInt() > maxValue.text.toString()
                        .toInt() -> {
                        maxValue.error = null
                        minValue.error = getString(R.string.ERROR_MIN_MORE_MAX)
                        setGenerate(false)
                        min = it.toInt()
                    }
                    TextUtils.isEmpty(maxValue.text.toString()) -> {
                        maxValue.error = getString(R.string.ERROR_EMPTY_MAX)
                        setGenerate(false)
                        min = it.toInt()
                    }
                    else -> {
                        maxValue.error = null
                        minValue.error = null
                        setGenerate(true)
                        min = it.toInt()
                    }
                }
            }
        }

        maxValue.doAfterTextChanged {
            maxValue.text.toString().let {
                when (true) {
                    TextUtils.isEmpty(it) -> {
                        maxValue.error = getString(R.string.ERROR_EMPTY)
                        setGenerate(false)
                    }
                    it.toDouble() > 2_147_483_647 ->{
                        maxValue.setText("")
                        maxValue.error = getString(R.string.ERROR_TOO_BIG)
                        setGenerate(false)
                    }
                    it.toInt() < 0 -> {
                        maxValue.error = getString(R.string.ERROR_TOO_SMALL)
                        setGenerate(false)
                    }
                    it.toIntOrNull() == null -> {
                        maxValue.error = getString(R.string.ERROR_INVALID_COMMON)
                        setGenerate(false)
                    }
                    !TextUtils.isEmpty(minValue.text.toString()) && it.toInt() < minValue.text.toString()
                        .toInt() -> {
                        minValue.error = null
                        maxValue.error = getString(R.string.ERROR_MIN_MORE_MAX)
                        setGenerate(false)
                        max = it.toInt()
                    }
                    TextUtils.isEmpty(minValue.text.toString()) -> {
                        minValue.error = getString(R.string.ERROR_EMPTY_MIN)
                        setGenerate(false)
                        max = it.toInt()
                    }
                    else -> {
                        maxValue.error = null
                        minValue.error = null
                        setGenerate(true)
                        max = it.toInt()
                    }
                }
            }
        }


        binding.generate.setOnClickListener {
            communicator.passSecondFragment(min, max)
        }
    }

    private fun setGenerate(status: Boolean){
        binding.generate.isEnabled = status
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}