/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.eucalypto.eucalyptapp.sleep.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import de.eucalypto.eucalyptapp.R
import de.eucalypto.eucalyptapp.databinding.FragmentSleepTrackerBinding
import de.eucalypto.eucalyptapp.sleep.database.SleepDatabase
import de.eucalypto.eucalyptapp.sleep.database.SleepNight

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database.
 */
class SleepTrackerFragment : Fragment() {

    lateinit var viewModel: SleepTrackerViewModel
    lateinit var binding: FragmentSleepTrackerBinding
    lateinit var adapter: SleepNightAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setupViewModel()
        setupBinding(inflater, container)
        setupRecyclerViewAdapter()
        setupObservers()

        return binding.root
    }

    private fun setupViewModel() {
        val application = this.requireActivity().application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SleepTrackerViewModel::class.java)
    }

    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentSleepTrackerBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupRecyclerViewAdapter() {
        val manager = GridLayoutManager(activity, 3)
        binding.sleepList.layoutManager = manager

        adapter = SleepNightAdapter(SleepNightListener { nightId ->
            viewModel.onSleepNightClicked(nightId)
        })
        binding.sleepList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.navigateToSleepQualityInput.observe(viewLifecycleOwner) { night ->
            navigateToSleepQualityInput(night)
        }

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
            showSnackBarEvent(it)
        }

        viewModel.navigateToSleepDataDetail.observe(viewLifecycleOwner) { nightId ->
            navigateToSleepDataDetail(nightId)
        }

        viewModel.nights.observe(viewLifecycleOwner) { nightsList ->
            adapter.submitList(nightsList)
        }
    }

    private fun navigateToSleepQualityInput(night: SleepNight?) {
        night?.let {
            findNavController().navigate(
                SleepTrackerFragmentDirections
                    .actionSleepShowQualityInput(it.nightId)
            )
            viewModel.completeNavigation()
        }
    }

    private fun showSnackBarEvent(it: Boolean) {
        if (!it) return
        Snackbar.make(
            binding.clearButton,
            getString(R.string.cleared_message),
            Snackbar.LENGTH_SHORT
        ).show()
        viewModel.doneShowingSnackbar()
    }

    private fun navigateToSleepDataDetail(nightId: Long?) {
        nightId?.let {
            this.findNavController()
                .navigate(SleepTrackerFragmentDirections.actionSleepTrackerToDetail(nightId))
            viewModel.onSleepDataDetailNavigated()
        }
    }
}
