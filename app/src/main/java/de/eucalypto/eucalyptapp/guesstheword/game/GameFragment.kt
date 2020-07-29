/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.eucalypto.eucalyptapp.guesstheword.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import de.eucalypto.eucalyptapp.databinding.FragmentGuessthewordGameBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    private lateinit var binding: FragmentGuessthewordGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = FragmentGuessthewordGameBinding.inflate(inflater, container, false)

        binding.gameViewModel = viewModel

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) gameFinished()
        })

        viewModel.secondsLeft.observe(viewLifecycleOwner, Observer { secondsLeft ->
            binding.timerText.text = DateUtils.formatElapsedTime(secondsLeft)
        })

        return binding.root
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action =
            GameFragmentDirections.actionGuessthewordShowScore(
                viewModel.score.value ?: 0
            )
        findNavController(this).navigate(action)
    }
}
