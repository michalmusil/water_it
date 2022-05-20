package cz.mendelu.xmusil5.waterit.ui.rooms.addoreditroom

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddOrEditRoomBinding
import kotlinx.coroutines.launch

class AddOrEditRoomFragment : BaseFragment<FragmentAddOrEditRoomBinding, AddOrEditRoomViewModel>(AddOrEditRoomViewModel::class) {
    val args: AddOrEditRoomFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentAddOrEditRoomBinding
        get() = FragmentAddOrEditRoomBinding::inflate

    override fun initViews() {
        setInteractionListeners()
        setOnSaveAction()

        viewModel.roomId = args.roomId
        if (viewModel.roomId >= 0) {
            lifecycleScope.launch {
                viewModel.fetchRoom()
            }.invokeOnCompletion {
                fillLayout()
            }
        } else{
            fillLayout()
        }
    }

    override fun onActivityCreated() {
    }

    private fun fillLayout(){
        binding.nameInput.text = viewModel.room.name

        viewModel.room.description?.let { binding.descriptionInput.text = viewModel.room.description!! }
    }

    private fun setOnSaveAction(){
        binding.saveRoomButton.setOnClickListener(View.OnClickListener {
            if (binding.nameInput.text.isBlank()){
                binding.nameInput.setError(getString(R.string.addRoom_error_mustEnterRoomName))
            } else{
                lifecycleScope.launch {
                    viewModel.saveRoom()
                }.invokeOnCompletion {
                    val directions = AddOrEditRoomFragmentDirections.actionAddOrEditRoomFragmentToRoomsListFragment()
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