package cz.mendelu.xmusil5.waterit.ui.plants.addplant

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
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
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import cz.mendelu.xmusil5.waterit.views.DatePickerView
import kotlinx.coroutines.launch


class AddPlantFragment : BaseFragment<FragmentAddPlantBinding, AddPlantViewModel>(AddPlantViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentAddPlantBinding
        get() = FragmentAddPlantBinding::inflate

    override fun initViews() {
        setInteractionListeners()
        setOnSaveAction()
    }

    override fun onActivityCreated() {
    }


    private fun setOnSaveAction(){
        binding.savePlantButton.setOnClickListener(View.OnClickListener {
            if (binding.nameInput.text.isBlank()){
                binding.nameInput.setError(getString(R.string.addPlant_error_mustEnterPlantName))
            } else if (binding.speciesInput.text.isBlank()){
                binding.speciesInput.setError(getString(R.string.addPlant_error_mustEnterPlantSpecies))
            } else{
                lifecycleScope.launch {
                    viewModel.savePlant()
                }.invokeOnCompletion {
                    val directions = AddPlantFragmentDirections.actionAddPlantFragmentToPlantsFragment()
                    findNavController().navigate(directions)
                }
            }
        })
    }


    private fun setInteractionListeners(){
        binding.nameInput.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
               viewModel.plant.name = text.toString()
            }
        })
        binding.speciesInput.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                viewModel.plant.species = text.toString()
            }
        })
        binding.descriptionInput.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                viewModel.plant.description = text.toString()
            }
        })

        binding.dateOfPlanting.setOnDateChangedListener(object: DatePickerView.CustomOnDateChangedListener{
            override fun onDateChanged(unixTime: Long?) {
                viewModel.plant.dateOfPlanting = unixTime
            }
        })

        binding.assignRoomButton.setOnClickListener(View.OnClickListener {
            // implements an onClickListener for when a room is selected
            var dialog = RoomsDialogFragment(object : RoomsDialogFragment.RoomOnClickListener{
                override fun onRoomClicked(room: DbRoom) {
                    viewModel.plant.roomId = room.id
                    binding.temporaryRoomName.text = room.name
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "rooms")
        })
    }

}