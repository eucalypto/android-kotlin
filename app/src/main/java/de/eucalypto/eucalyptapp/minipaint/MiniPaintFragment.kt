package de.eucalypto.eucalyptapp.minipaint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import de.eucalypto.eucalyptapp.databinding.FragmentMiniPaintBinding

class MiniPaintFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMiniPaintBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}