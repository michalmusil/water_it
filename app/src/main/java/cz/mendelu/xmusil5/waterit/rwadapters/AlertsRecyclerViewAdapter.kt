package cz.mendelu.xmusil5.waterit.rwadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.xmusil5.waterit.alerts.models.AlertModel
import cz.mendelu.xmusil5.waterit.databinding.ListItemAlertBinding

class AlertsRecyclerViewAdapter(private var alertsList: MutableList<AlertModel>): RecyclerView.Adapter<AlertsRecyclerViewAdapter.AlertViewHolder>(){

    inner class AlertViewHolder(val binding: ListItemAlertBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        return AlertViewHolder(ListItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val bindedAlert = alertsList.get(position)

        holder.binding.plantName.text = bindedAlert.plant.name
        holder.binding.supposedToWaterDate.text = bindedAlert.supposedWateringDateString
        holder.binding.supposedToWaterDay.text = bindedAlert.supposedWateringDayString

        holder.binding.checked.setOnCheckedChangeListener(
            object : CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    bindedAlert.checked = p1
                }
            })
    }

    override fun getItemCount(): Int {
        return alertsList.size
    }
}