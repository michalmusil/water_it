package cz.mendelu.xmusil5.waterit.ui.plants.plantdetail

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantDetailBinding
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import kotlinx.coroutines.launch


class PlantDetailFragment : BaseFragment<FragmentPlantDetailBinding, PlantDetailViewModel>(PlantDetailViewModel::class) {

    private val args: PlantDetailFragmentArgs by navArgs()


    override val bindingInflater: (LayoutInflater) -> FragmentPlantDetailBinding
        get() = FragmentPlantDetailBinding::inflate

    override fun initViews() {
        viewModel.plantId = this.args.plantId
        if (viewModel.plantId>=0){
            lifecycleScope.launch {
                viewModel.fetchPlant()
            }.invokeOnCompletion {
                fillLayout()
            }
        } else{
            // do an error fix to prevent this from EVER HAPPENING
            //probably just go back
        }
    }

    override fun onActivityCreated() {

    }

    private fun fillLayout(){
        binding.name.attributeText = viewModel.plant.name
        binding.species.attributeText = viewModel.plant.species
        viewModel.plant.roomId?.let { binding.room.attributeText = viewModel.plant.roomId.toString() }
        viewModel.plant.dateOfPlanting?.let { binding.datePlanted.attributeText = DateUtils.getDateString(viewModel.plant.dateOfPlanting!!) }
        viewModel.plant.lastWatered?.let { binding.lastWatered.attributeText = DateUtils.getDateString(viewModel.plant.lastWatered!!) }
        viewModel.plant.daysBetweenWatering?.let { binding.daysBetweenWatering.attributeText = viewModel.plant.daysBetweenWatering.toString() }
        viewModel.plant.description?.let { binding.description.attributeText = viewModel.plant.description.toString() }
    }

}