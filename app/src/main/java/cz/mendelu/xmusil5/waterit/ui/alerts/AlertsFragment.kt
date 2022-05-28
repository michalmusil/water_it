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
import cz.mendelu.xmusil5.waterit.rwadapters.AlertsRecyclerViewAdapter
import kotlinx.coroutines.launch


class AlertsFragment : BaseFragment<FragmentAlertsBinding, AlertsViewModel>(AlertsViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentAlertsBinding
        get() = FragmentAlertsBinding::inflate

    override fun initViews() {
        lifecycleScope.launch {
            viewModel.loadAlerts()
        }.invokeOnCompletion {
            viewModel.layoutManager = GridLayoutManager(requireContext(), 2)
            viewModel.alertsAdapter = AlertsRecyclerViewAdapter(viewModel.alerts)

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



}