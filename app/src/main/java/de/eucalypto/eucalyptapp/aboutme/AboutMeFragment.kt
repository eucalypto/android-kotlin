package de.eucalypto.eucalyptapp.aboutme

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import de.eucalypto.eucalyptapp.R
import de.eucalypto.eucalyptapp.databinding.FragmentAboutMeBinding
import kotlinx.android.synthetic.main.fragment_about_me.*

class AboutMeFragment : Fragment() {

    private var _binding: FragmentAboutMeBinding? = null
    private val binding: FragmentAboutMeBinding
        get() {
            return _binding ?: throw NullPointerException(
                "View binding only allowed in Fragment's view lifecycle: between onCreateView and onDestroyView"
            )
        }

    private val myName = MyName("Gandalf the Grey")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The following could also be done in onCreateView:
        // _binding = FragmentAboutMeBinding.inflate(inflater, container, false)
        // return binding.root
        _binding = FragmentAboutMeBinding.bind(view)

        binding.myName = myName

        binding.doneButton.setOnClickListener { addNickname() }
    }

    private fun addNickname() {
        myName.nickname = nicknameEdit.text.toString()
        binding.invalidateAll()
        binding.nicknameEdit.visibility = View.GONE
        binding.doneButton.visibility = View.GONE
        binding.nicknameText.visibility = View.VISIBLE

        // Hide keyboard
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.doneButton.windowToken, 0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}