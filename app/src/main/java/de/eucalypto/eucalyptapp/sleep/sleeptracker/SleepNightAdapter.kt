package de.eucalypto.eucalyptapp.sleep.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.eucalypto.eucalyptapp.R
import de.eucalypto.eucalyptapp.databinding.ListItemSleepNightBinding
import de.eucalypto.eucalyptapp.sleep.convertDurationToFormatted
import de.eucalypto.eucalyptapp.sleep.convertNumericQualityToString
import de.eucalypto.eucalyptapp.sleep.database.SleepNight

class SleepNightAdapter
    : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val night = getItem(position)
        holder.bind(night)
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(night: SleepNight) {
            val res = itemView.context.resources
            binding.sleepLength.text =
                convertDurationToFormatted(night.startTimeMilli, night.endTimeMilli, res)
            binding.qualityString.text = convertNumericQualityToString(night.sleepQuality, res)
            binding.qualityImage.setImageResource(
                when (night.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
}


