package cz.mendelu.xmusil5.waterit.ui.plants.plantsList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantsBinding
import cz.mendelu.xmusil5.waterit.databinding.ListItemPlantBinding
import cz.mendelu.xmusil5.waterit.utils.PictureUtils


class PlantsFragment : BaseFragment<FragmentPlantsBinding, PlantsViewModel>(PlantsViewModel::class) {

    private val plantsList: MutableList<DbPlant> = mutableListOf()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var plantsAdapter: PlantsRecyclerViewAdapter

    override val bindingInflater: (LayoutInflater) -> FragmentPlantsBinding
        get() = FragmentPlantsBinding::inflate

    override fun initViews() {
        this.layoutManager = LinearLayoutManager(requireContext())
        this.plantsAdapter = PlantsRecyclerViewAdapter()

        val rw = this.binding.plantsRecyclerView
        rw.layoutManager = this.layoutManager
        rw.adapter = this.plantsAdapter
        this.viewModel.getAll().observe(viewLifecycleOwner, object: Observer<MutableList<DbPlant>>{
            override fun onChanged(t: MutableList<DbPlant>?) {
                //updating views in recyclerview
                val callback = PlantsDiffUtils(plantsList, t!!)
                val result = DiffUtil.calculateDiff(callback)
                result.dispatchUpdatesTo(plantsAdapter)

                plantsList.clear()
                plantsList.addAll(t!!)
            }

        })

        binding.addPlantFab.setOnClickListener(View.OnClickListener {
            val directions = PlantsFragmentDirections.actionPlantsFragmentToAddPlantFragment()
            findNavController().navigate(directions)
        })
    }

    override fun onActivityCreated() {

    }




    inner class PlantsRecyclerViewAdapter: RecyclerView.Adapter<PlantsRecyclerViewAdapter.PlantViewHolder>(){

        inner class PlantViewHolder(val binding: ListItemPlantBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
            return PlantViewHolder(ListItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
            val bindedPlant = plantsList.get(position)

            holder.binding.root.setOnClickListener(View.OnClickListener {
                val directions = PlantsFragmentDirections.actionPlantsFragmentToPlantDetailFragment(bindedPlant.id!!)
                findNavController().navigate(directions)
            })

            holder.binding.plantName.text = bindedPlant.name
            holder.binding.plantSpecies.text = bindedPlant.species
            if(bindedPlant.picture != null) {
                holder.binding.plantImageContainer.setImageBitmap(
                    PictureUtils.fromByteArrayToBitmap(bindedPlant.picture))
            }
        }

        override fun getItemCount(): Int {
            return plantsList.size
        }
    }

    //Class for comparing two lists - to find out which items to update in the adapter
    inner class PlantsDiffUtils(private val oldList: MutableList<DbPlant>,
                              private val newList: MutableList<DbPlant>): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
                    && oldList[oldItemPosition].species == newList[newItemPosition].species
                    && oldList[oldItemPosition].roomId == newList[newItemPosition].roomId
                    && oldList[oldItemPosition].picture.contentEquals(newList[newItemPosition].picture)
                    && oldList[oldItemPosition].dateOfPlanting == newList[newItemPosition].dateOfPlanting
                    && oldList[oldItemPosition].lastWatered == newList[newItemPosition].lastWatered
                    && oldList[oldItemPosition].daysBetweenWatering == newList[newItemPosition].daysBetweenWatering
                    && oldList[oldItemPosition].description == newList[newItemPosition].description
        }
    }
}