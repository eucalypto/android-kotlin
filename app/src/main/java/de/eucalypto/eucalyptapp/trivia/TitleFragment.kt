package de.eucalypto.eucalyptapp.trivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import de.eucalypto.eucalyptapp.R
import de.eucalypto.eucalyptapp.databinding.FragmentTriviaTitleBinding

class TitleFragment : Fragment() {

    private var _binding: FragmentTriviaTitleBinding? = null
    private val binding: FragmentTriviaTitleBinding
        get() {
            return _binding
                ?: throw NullPointerException("Binding is only accessible between onCreateView and onDestroyView")
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_trivia_title, container, false)
        _binding = FragmentTriviaTitleBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(TitleFragmentDirections.actionTriviaStartGame())
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController(this))
            || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}