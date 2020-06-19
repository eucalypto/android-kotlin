package com.example.android.eucalyptapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.android.eucalyptapp.databinding.FragmentAboutMeBinding

class AboutMeFragment : Fragment() {

    private var _fragmentAboutMeBinding: FragmentAboutMeBinding? = null
    private val fragmentAboutMeBinding: FragmentAboutMeBinding
        get() {
            return _fragmentAboutMeBinding ?: throw NullPointerException(
                "View binding only allowed in Fragment's view lifecycle: between onCreateView and onDestroyView"
            )
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _fragmentAboutMeBinding = FragmentAboutMeBinding.bind(view)

        fragmentAboutMeBinding.doneButton.setOnClickListener { addNickname() }
    }

    private fun addNickname() {
        fragmentAboutMeBinding.nicknameText.text = fragmentAboutMeBinding.nicknameEdit.text
        fragmentAboutMeBinding.nicknameEdit.visibility = View.GONE
        fragmentAboutMeBinding.doneButton.visibility = View.GONE
        fragmentAboutMeBinding.nicknameText.visibility = View.VISIBLE

        // Hide keyboard
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(fragmentAboutMeBinding.doneButton.windowToken, 0)
    }

    override fun onDestroyView() {
        _fragmentAboutMeBinding = null
        super.onDestroyView()
    }
}