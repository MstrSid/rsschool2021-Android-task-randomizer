package com.rsschool.android2021

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rsschool.android2021.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var communicator: Communicator
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        //add back system button for pass result and back to first fragment
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    communicator.passFirstFragment(binding.result.text.toString().toInt())
                }
            })


        communicator = activity as Communicator
        binding.apply {
        result.text = generate(min, max).toString()
        if (result.text == "666"){
            result.setTextColor(Color.RED)
            Snackbar.make(result, R.string.SATAN, Snackbar.LENGTH_LONG).setTextColor(Color.RED).show()
        }
    }


    // pass result and back to first fragment
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun generate(min: Int, max: Int): Int {
        return (min..max).random()
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}