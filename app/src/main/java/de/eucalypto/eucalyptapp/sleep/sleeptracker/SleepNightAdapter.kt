package de.eucalypto.eucalyptapp.sleep.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.eucalypto.eucalyptapp.R
import de.eucalypto.eucalyptapp.sleep.convertDurationToFormatted
import de.eucalypto.eucalyptapp.sleep.convertNumericQualityToString
import de.eucalypto.eucalyptapp.sleep.database.SleepNight
import kotlinx.android.synthetic.main.list_item_sleep_night.view.*

class SleepNightAdapter
    : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {

    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val night = data[position]
        val res = holder.itemView.context.resources
        holder.sleepLength.text =
            convertDurationToFormatted(night.startTimeMilli, night.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(night.sleepQuality, res)
        holder.qualityImage.setImageResource(
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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sleepLength: TextView = itemView.sleep_length
        val quality: TextView = itemView.quality_string
        val qualityImage: ImageView = itemView.quality_image
    }
}


