package cz.mendelu.xmusil5.waterit.ui.home

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.databinding.FragmentHomeBinding
import cz.mendelu.xmusil5.waterit.rwadapters.AlertsRecyclerViewAdapter
import cz.mendelu.xmusil5.waterit.rwadapters.PlantsRecyclerViewAdapter
import cz.mendelu.xmusil5.waterit.utils.RecyclerViewUtils
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class) {
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun initViews() {
        lifecycleScope.launch {
            viewModel.loadAlerts()
            viewModel.loadMostRecentlyPlanted()
        }.invokeOnCompletion {
            if (viewModel.alerts.size < 1){
                binding.alertsTitle.visibility = View.GONE
            }
            if (viewModel.plantsList.size < 1){
                binding.plantsTitle.visibility = View.GONE
            }
            setupAlertsRw()
            setupPlantsRw()
        }

    }

    override fun onActivityCreated() {
    }

    override fun onFragmentViewDestroyed() {
        viewModel.updateCheckedAlerts()
    }

    override fun onPause() {
        viewModel.updateCheckedAlerts()
        super.onPause()
    }

    private fun setupAlertsRw(){
        viewModel.alertsLayoutManager = GridLayoutManager(requireContext(),
            RecyclerViewUtils.columnCountAlerts(requireActivity().resources.displayMetrics))
        viewModel.alertsAdapter = AlertsRecyclerViewAdapter(viewModel.alerts)

        val rw = binding.alertsRecyclerView
        rw.layoutManager = viewModel.alertsLayoutManager
        rw.adapter = viewModel.alertsAdapter
    }


    private fun setupPlantsRw(){
        lifecycleScope.launch {
            viewModel.loadMostRecentlyPlanted()
        }.invokeOnCompletion {
            viewModel.plantsLayoutManager = LinearLayoutManager(requireContext())
            viewModel.plantsAdapter = PlantsRecyclerViewAdapter(requireContext(), viewModel.plantsList, object: PlantsRecyclerViewAdapter.PlantsRecyclerViewEventListener{
                override fun onItemClicked(plant: DbPlant) {
                    val directions = HomeFragmentDirections.actionHomeFragmentToPlantDetailFragment(plant.id!!)
                    findNavController().navigate(directions)
                }
            })
            val plantsRw = this.binding.plantsRecyclerView
            plantsRw.layoutManager = viewModel.plantsLayoutManager
            plantsRw.adapter = viewModel.plantsAdapter
        }
    }


}