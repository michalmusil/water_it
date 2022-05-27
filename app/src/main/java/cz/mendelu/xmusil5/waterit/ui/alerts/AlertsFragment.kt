package cz.mendelu.xmusil5.waterit.ui.alerts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.GridLayout.VERTICAL
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentAlertsBinding
import cz.mendelu.xmusil5.waterit.databinding.ListItemAlertBinding
import kotlinx.coroutines.launch


class AlertsFragment : BaseFragment<FragmentAlertsBinding, AlertsViewModel>(AlertsViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentAlertsBinding
        get() = FragmentAlertsBinding::inflate

    override fun initViews() {
        lifecycleScope.launch {
            viewModel.loadAlerts()
        }.invokeOnCompletion {
            viewModel.layoutManager = GridLayoutManager(requireContext(), 2)
            viewModel.alertsAdapter = AlertsRecyclerViewAdapter()

            val rw = binding.alertsRecyclerView
            rw.layoutManager = viewModel.layoutManager
            rw.adapter = viewModel.alertsAdapter
        }
    }

    override fun onActivityCreated() {
    }

    override fun onFragmentViewDestroyed() {
        viewModel.updateCheckedAlerts()
    }

    inner class AlertsRecyclerViewAdapter(): RecyclerView.Adapter<AlertsRecyclerViewAdapter.AlertViewHolder>(){

        inner class AlertViewHolder(val binding: ListItemAlertBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
            return AlertViewHolder(ListItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
            val bindedAlert = viewModel.alerts.get(position)

            holder.binding.plantName.text = bindedAlert.plant.name
            holder.binding.supposedToWaterDate.text = bindedAlert.supposedWateringDateString
            holder.binding.supposedToWaterDay.text = bindedAlert.supposedWateringDayString

            holder.binding.checked.setOnCheckedChangeListener(
                object : CompoundButton.OnCheckedChangeListener{
                    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                        lifecycleScope.launch {
                            bindedAlert.checked = p1
                        }

                    }
                })
        }

        override fun getItemCount(): Int {
            return viewModel.alerts.size
        }
    }

}