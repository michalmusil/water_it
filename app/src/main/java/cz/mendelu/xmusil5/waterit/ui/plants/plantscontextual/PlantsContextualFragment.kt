package cz.mendelu.xmusil5.waterit.ui.plants.plantscontextual

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantsListBinding
import cz.mendelu.xmusil5.waterit.rwadapters.PlantsRecyclerViewAdapter
import cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail.RoomDetailFragmentArgs
import kotlinx.coroutines.launch

class PlantsContextualFragment: BaseFragment<FragmentPlantsListBinding, PlantsContextualViewModel>(PlantsContextualViewModel::class) {
    private val args: RoomDetailFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentPlantsListBinding
        get() = FragmentPlantsListBinding::inflate

    override fun initViews() {
        binding.addPlantFab.visibility = View.GONE
        lifecycleScope.launch {
            loadArgumentModels()
            if (viewModel.room != null){
                viewModel.layoutManager = LinearLayoutManager(requireContext())
                viewModel.plantsAdapter = PlantsRecyclerViewAdapter(viewModel.plantsList, object: PlantsRecyclerViewAdapter.PlantsRecyclerViewEventListener{
                    override fun onItemClicked(plant: DbPlant) {
                        val directions = PlantsContextualFragmentDirections.actionPlantsContextualFragmentToPlantDetailFragment(plant.id!!)
                        findNavController().navigate(directions)
                    }
                })

                val rw = binding.plantsRecyclerView
                rw.layoutManager = viewModel.layoutManager
                rw.adapter = viewModel.plantsAdapter

                val tempList = viewModel.getAllWithRoomId(viewModel.room!!.id!!)
                tempList.observe(viewLifecycleOwner, object:
                    Observer<MutableList<DbPlant>> {
                    override fun onChanged(t: MutableList<DbPlant>?) {
                        //updating views in recyclerview
                        val callback = viewModel.plantsAdapter.PlantsDiffUtils(viewModel.plantsList, t!!)
                        val result = DiffUtil.calculateDiff(callback)
                        result.dispatchUpdatesTo(viewModel.plantsAdapter)

                        viewModel.plantsList.clear()
                        viewModel.plantsList.addAll(t!!)
                    }

                })
            } else{
                // DO SOMETHING (PROBABLY JUST GO BACK TO PREVENT THIS STATE)
            }
        }
    }

    override fun onActivityCreated() {

    }

    private suspend fun loadArgumentModels(){
        val roomId = this.args.roomId
        if (roomId >= 0){
            viewModel.room = viewModel.getRoomById(roomId)
        }
    }

}