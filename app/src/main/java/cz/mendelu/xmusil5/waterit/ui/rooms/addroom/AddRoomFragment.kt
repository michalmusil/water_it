package cz.mendelu.xmusil5.waterit.ui.rooms.addroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbPlant
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddPlantBinding
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddRoomBinding
import cz.mendelu.xmusil5.waterit.ui.plants.addplant.AddPlantFragmentDirections
import kotlinx.coroutines.launch

class AddRoomFragment : BaseFragment<FragmentAddRoomBinding, AddRoomViewModel>(AddRoomViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentAddRoomBinding
        get() = FragmentAddRoomBinding::inflate

    override fun initViews() {
        binding.saveRoomButton.setOnClickListener(View.OnClickListener {
            val roomName = binding.nameInput.text
            val roomDescription = binding.descriptionInput.text

            if (roomName.isBlank()){
                binding.nameInput.setError(getString(R.string.addRoom_error_mustEnterRoomName))
            } else{
                val newRoom = DbRoom(roomName)
                newRoom.description = roomDescription

                lifecycleScope.launch {
                    viewModel.addRoom(newRoom)
                    val directions = AddRoomFragmentDirections.actionAddRoomFragmentToRoomsListFragment()
                    findNavController().navigate(directions)
                }
            }
        })
    }

    override fun onActivityCreated() {

    }
}