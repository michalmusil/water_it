package cz.mendelu.xmusil5.waterit.ui.rooms.addroom

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        setInteractionListeners()
        setOnSaveAction()
    }

    override fun onActivityCreated() {
    }

    private fun setOnSaveAction(){
        binding.saveRoomButton.setOnClickListener(View.OnClickListener {
            if (binding.nameInput.text.isBlank()){
                binding.nameInput.setError(getString(R.string.addRoom_error_mustEnterRoomName))
            } else{
                lifecycleScope.launch {
                    viewModel.saveRoom()
                }.invokeOnCompletion {
                    val directions = AddRoomFragmentDirections.actionAddRoomFragmentToRoomsListFragment()
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
                viewModel.room.name = text.toString()
            }
        })
        binding.descriptionInput.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                viewModel.room.description = text.toString()
            }
        })
    }
}