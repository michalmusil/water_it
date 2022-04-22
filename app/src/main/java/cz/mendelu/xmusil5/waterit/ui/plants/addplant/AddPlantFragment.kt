package cz.mendelu.xmusil5.waterit.ui.plants.addplant

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddPlantBinding
import kotlinx.coroutines.launch


class AddPlantFragment : BaseFragment<FragmentAddPlantBinding, AddPlantViewModel>(AddPlantViewModel::class) {


    override val bindingInflater: (LayoutInflater) -> FragmentAddPlantBinding
        get() = FragmentAddPlantBinding::inflate

    override fun initViews() {
        binding.savePlantButton.setOnClickListener(View.OnClickListener {
            val plantName = binding.nameInput.text
            val plantSpecies = binding.speciesInput.text
            val plantDescription = binding.descriptionInput.text

            if (plantName.isBlank()){
                binding.nameInput.setError(getString(R.string.addPlant_error_mustEnterPlantName))
            } else if (plantSpecies.isBlank()){
                binding.speciesInput.setError(getString(R.string.addPlant_error_mustEnterPlantSpecies))
            } else{
                val newPlant = DbPlant(plantName, plantSpecies)
                newPlant.description = plantDescription

                lifecycleScope.launch {
                    viewModel.addPlant(newPlant)
                    val directions = AddPlantFragmentDirections.actionAddPlantFragmentToPlantsFragment()
                    findNavController().navigate(directions)
                }
            }
        })
    }

    override fun onActivityCreated() {

    }


}