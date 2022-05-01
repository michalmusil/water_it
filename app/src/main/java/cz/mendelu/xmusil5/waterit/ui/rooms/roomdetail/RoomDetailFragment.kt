package cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentRoomDetailBinding
import kotlinx.coroutines.launch


class RoomDetailFragment : BaseFragment<FragmentRoomDetailBinding, RoomDetailViewModel>(RoomDetailViewModel::class) {
    private val args: RoomDetailFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentRoomDetailBinding
        get() = FragmentRoomDetailBinding::inflate

    override fun initViews() {
        val roomId = this.args.roomId
        if (roomId>=0){
            lifecycleScope.launch {
                viewModel.room = viewModel.findById(roomId)

                binding.name.attributeText = viewModel.room.name
                viewModel.room.longitude?.let { binding.longitude.attributeText = viewModel.room.longitude.toString() }
                viewModel.room.latitude?.let { binding.latitude.attributeText = viewModel.room.latitude.toString() }
                viewModel.room.description?.let { binding.description.attributeText = viewModel.room.description.toString() }
            }
        } else{
            // do an error fix to prevent this from EVER HAPPENING
            //probably just go back
        }

        binding.showPlantsButton.setOnClickListener(View.OnClickListener {
            val directions = RoomDetailFragmentDirections.actionRoomDetailFragmentToPlantsContextualFragment(roomId)
            findNavController().navigate(directions)
        })
    }

    override fun onActivityCreated() {

    }

}