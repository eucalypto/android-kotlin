package com.example.android.eucalyptapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.eucalyptapp.databinding.FragmentColorMyViewsBinding

class ColorMyViewsFragment : Fragment() {

    private var _binding: FragmentColorMyViewsBinding? = null
    private val binding: FragmentColorMyViewsBinding
        get() {
            return _binding
                ?: throw NullPointerException("View binding only allowed in Fragment's view lifecycle: between onCreateView and onDestroyView")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_my_views, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentColorMyViewsBinding.bind(view)

        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<View> = listOf(
            binding.textView1,
            binding.textView2,
            binding.textView3,
            binding.textView4,
            binding.textView5,
            binding.colorMyViewsLayout,
            binding.greenButton,
            binding.redButton,
            binding.yellowButton
        )

        for (item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }
    }

    private fun makeColored(view: View) {
        when (view.id) {
            R.id.textView1 -> view.setBackgroundColor(Color.DKGRAY)
            R.id.textView2 -> view.setBackgroundColor(Color.GRAY)

            R.id.textView3 -> view.setBackgroundResource(android.R.color.holo_green_light)
            R.id.textView4 -> view.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.textView5 -> view.setBackgroundResource(android.R.color.holo_green_light)

            R.id.red_button -> binding.textView3.setBackgroundResource(R.color.my_red)
            R.id.green_button -> binding.textView5.setBackgroundResource(R.color.my_green)
            R.id.yellow_button -> binding.textView4.setBackgroundResource(R.color.my_yellow)

            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}