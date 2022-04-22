package cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentRoomDetailBinding
import kotlinx.coroutines.launch


class RoomDetailFragment : BaseFragment<FragmentRoomDetailBinding, RoomDetailViewModel>(RoomDetailViewModel::class) {
    private val args: RoomDetailFragmentArgs by navArgs()
    private lateinit var room: DbRoom

    override val bindingInflater: (LayoutInflater) -> FragmentRoomDetailBinding
        get() = FragmentRoomDetailBinding::inflate

    override fun initViews() {
        val roomId = this.args.roomId
        if (roomId>=0){
            lifecycleScope.launch {
                room = viewModel.findById(roomId)

                binding.name.attributeText = room.name
                room.longitude?.let { binding.longitude.attributeText = room.longitude.toString() }
                room.latitude?.let { binding.latitude.attributeText = room.latitude.toString() }
                room.description?.let { binding.description.attributeText = room.description.toString() }
            }
        } else{
            // do an error fix to prevent this from EVER HAPPENING
            //probably just go back
        }
    }

    override fun onActivityCreated() {

    }

}