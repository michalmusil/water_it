package cz.mendelu.xmusil5.waterit.ui.plants.plantsList


import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantsListBinding
import cz.mendelu.xmusil5.waterit.rwadapters.PlantsRecyclerViewAdapter


class PlantsListFragment : BaseFragment<FragmentPlantsListBinding, PlantsListViewModel>(PlantsListViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentPlantsListBinding
        get() = FragmentPlantsListBinding::inflate

    override fun initViews() {
        viewModel.layoutManager = LinearLayoutManager(requireContext())
        viewModel.plantsAdapter = PlantsRecyclerViewAdapter(requireContext(), viewModel.plantsList, object: PlantsRecyclerViewAdapter.PlantsRecyclerViewEventListener{
            override fun onItemClicked(plant: DbPlant) {
                val directions = PlantsListFragmentDirections.actionPlantsFragmentToPlantDetailFragment(plant.id!!)
                findNavController().navigate(directions)
            }
        })

        val rw = this.binding.plantsRecyclerView
        rw.layoutManager = viewModel.layoutManager
        rw.adapter = viewModel.plantsAdapter

        viewModel.getAll().observe(viewLifecycleOwner, object: Observer<MutableList<DbPlant>>{
            override fun onChanged(t: MutableList<DbPlant>?) {
                //updating views in recyclerview
                val callback = viewModel.plantsAdapter.PlantsDiffUtils(viewModel.plantsList, t!!)
                val result = DiffUtil.calculateDiff(callback)
                result.dispatchUpdatesTo(viewModel.plantsAdapter)

                viewModel.plantsList.clear()
                viewModel.plantsList.addAll(t!!)
            }

        })

        binding.addPlantFab.setOnClickListener(View.OnClickListener {
            val directions = PlantsListFragmentDirections.actionPlantsFragmentToAddOrEditPlantFragment()
            findNavController().navigate(directions)
        })
    }

    override fun onActivityCreated() {

    }
}