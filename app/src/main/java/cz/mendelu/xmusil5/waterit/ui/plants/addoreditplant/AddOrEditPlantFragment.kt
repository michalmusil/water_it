package cz.mendelu.xmusil5.waterit.ui.plants.addoreditplant

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddOrEditPlantBinding
import cz.mendelu.xmusil5.waterit.ui.dialogfragments.rooms.RoomsDialogFragment
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import cz.mendelu.xmusil5.waterit.views.DatePickerView
import kotlinx.coroutines.launch


class AddOrEditPlantFragment : BaseFragment<FragmentAddOrEditPlantBinding, AddOrEditPlantViewModel>(AddOrEditPlantViewModel::class) {

    private val args: AddOrEditPlantFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentAddOrEditPlantBinding
        get() = FragmentAddOrEditPlantBinding::inflate

    override fun initViews() {
        viewModel.plantId = args.plantId
        if (viewModel.plantId >= 0){
            lifecycleScope.launch{
                viewModel.fetchPlant()
            }.invokeOnCompletion {
                fillLayout()
            }
        }else{
            fillLayout()
        }
        setInteractionListeners()
        setOnSaveAction()
    }

    override fun onActivityCreated() {
    }

    private fun fillLayout(){
        binding.nameInput.text = viewModel.plantWithRoom.plant.name
        binding.speciesInput.text = viewModel.plantWithRoom.plant.species

        viewModel.plantWithRoom.room?.name?.let { binding.temporaryRoomName.text = viewModel.plantWithRoom.room!!.name }
        viewModel.plantWithRoom.plant.dateOfPlanting?.let { binding.dateOfPlanting.datePickText = DateUtils.getDateString(viewModel.plantWithRoom.plant.dateOfPlanting!!) }
        viewModel.plantWithRoom.plant.description?.let { binding.descriptionInput.text = viewModel.plantWithRoom.plant.description!! }
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
                    val directions = AddOrEditPlantFragmentDirections.actionAddOrEditPlantFragmentToPlantsFragment()
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
               viewModel.plantWithRoom.plant.name = text.toString()
            }
        })
        binding.speciesInput.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                viewModel.plantWithRoom.plant.species = text.toString()
            }
        })
        binding.descriptionInput.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                viewModel.plantWithRoom.plant.description = text.toString()
            }
        })

        binding.dateOfPlanting.setOnDateChangedListener(object: DatePickerView.CustomOnDateChangedListener{
            override fun onDateChanged(unixTime: Long?) {
                viewModel.plantWithRoom.plant.dateOfPlanting = unixTime
            }
        })

        binding.assignRoomButton.setOnClickListener(View.OnClickListener {
            // implements an onClickListener for when a room is selected
            var dialog = RoomsDialogFragment(object : RoomsDialogFragment.RoomOnClickListener{
                override fun onRoomClicked(room: DbRoom) {
                    viewModel.plantWithRoom.room = room
                    viewModel.plantWithRoom.plant.roomId = room.id
                    binding.temporaryRoomName.text = room.name
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "rooms")
        })
    }

}