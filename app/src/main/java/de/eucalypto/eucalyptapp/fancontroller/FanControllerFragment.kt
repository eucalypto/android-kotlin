package de.eucalypto.eucalyptapp.fancontroller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.eucalypto.eucalyptapp.databinding.FragmentFanControllerBinding

class FanControllerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFanControllerBinding.inflate(inflater, container, false)

        return binding.root
    }
}