package cz.mendelu.xmusil5.waterit.ui.plants.plantdetail

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantDetailBinding
import kotlinx.coroutines.launch


class PlantDetailFragment : BaseFragment<FragmentPlantDetailBinding, PlantDetailViewModel>(PlantDetailViewModel::class) {

    private val args: PlantDetailFragmentArgs by navArgs()
    private lateinit var plant: DbPlant

    override val bindingInflater: (LayoutInflater) -> FragmentPlantDetailBinding
        get() = FragmentPlantDetailBinding::inflate

    override fun initViews() {
        val plantId = this.args.plantId
        if (plantId>=0){
            lifecycleScope.launch {
                plant = viewModel.findById(plantId)

                binding.name.attributeText = plant.name
                binding.species.attributeText = plant.species
                plant.roomId?.let { binding.room.attributeText = plant.roomId.toString() }
                plant.dateOfPlanting?.let { binding.datePlanted.attributeText = plant.dateOfPlanting.toString() }
                plant.lastWatered?.let { binding.lastWatered.attributeText = plant.lastWatered.toString() }
                plant.dateOfPlanting?.let { binding.daysBetweenWatering.attributeText = plant.daysBetweenWatering.toString() }
                plant.description?.let { binding.description.attributeText = plant.description.toString() }
            }
        } else{
            // do an error fix to prevent this from EVER HAPPENING
            //probably just go back
        }
    }

    override fun onActivityCreated() {

    }


}