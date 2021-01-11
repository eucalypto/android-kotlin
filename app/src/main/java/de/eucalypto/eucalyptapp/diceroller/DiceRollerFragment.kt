package de.eucalypto.eucalyptapp.diceroller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.eucalypto.eucalyptapp.R
import de.eucalypto.eucalyptapp.databinding.FragmentDiceRollerBinding
import java.util.Random

class DiceRollerFragment : Fragment() {

    lateinit var binding: FragmentDiceRollerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentDiceRollerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rollButton.text = getString(R.string.roll_button_text)
        binding.rollButton.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val diceResource = when (Random().nextInt(6) + 1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        binding.diceImage.setImageResource(diceResource)
    }
}