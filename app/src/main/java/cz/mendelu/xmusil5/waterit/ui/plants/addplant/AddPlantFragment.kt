package cz.mendelu.xmusil5.waterit.ui.plants.addplant

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddPlantBinding
import cz.mendelu.xmusil5.waterit.ui.dialogfragments.rooms.RoomsDialogFragment
import kotlinx.coroutines.launch


class AddPlantFragment : BaseFragment<FragmentAddPlantBinding, AddPlantViewModel>(AddPlantViewModel::class) {

    private var selectedRoomId: Long = -1

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
                newPlant.roomId = if(selectedRoomId>=0) selectedRoomId else null

                lifecycleScope.launch {
                    viewModel.addPlant(newPlant)
                    val directions = AddPlantFragmentDirections.actionAddPlantFragmentToPlantsFragment()
                    findNavController().navigate(directions)
                }
            }
        })

        binding.assignRoomButton.setOnClickListener(View.OnClickListener {
            // implements an onClickListener for when a room is selected
            var dialog = RoomsDialogFragment(object : RoomsDialogFragment.RoomOnClickListener{
                override fun onRoomClicked(room: DbRoom) {
                    selectedRoomId = room.id!!
                    binding.temporaryRoomName.text = room.name
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "rooms")
        })


    }

    override fun onActivityCreated() {

    }

}