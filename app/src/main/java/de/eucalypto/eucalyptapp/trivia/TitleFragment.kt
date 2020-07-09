package de.eucalypto.eucalyptapp.trivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import de.eucalypto.eucalyptapp.R
import de.eucalypto.eucalyptapp.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private var _binding: FragmentTitleBinding? = null
    private val binding: FragmentTitleBinding
        get() {
            return _binding
                ?: throw NullPointerException("Binding is only accessible between onCreateView and onDestroyView")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_title, container, false)
        _binding = FragmentTitleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}