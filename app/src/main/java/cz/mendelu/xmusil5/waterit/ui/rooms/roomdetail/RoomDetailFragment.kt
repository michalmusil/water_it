package cz.mendelu.xmusil5.waterit.ui.rooms.roomdetail

import android.app.AlertDialog
import android.graphics.drawable.BitmapDrawable
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentRoomDetailBinding
import cz.mendelu.xmusil5.waterit.utils.PictureUtils
import kotlinx.coroutines.launch


class RoomDetailFragment : BaseFragment<FragmentRoomDetailBinding, RoomDetailViewModel>(RoomDetailViewModel::class) {
    private val args: RoomDetailFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentRoomDetailBinding
        get() = FragmentRoomDetailBinding::inflate

    override fun initViews() {
        setHasOptionsMenu(true)

        viewModel.roomId = this.args.roomId
        if (viewModel.roomId>=0){
            lifecycleScope.launch {
                viewModel.fetchRoom()
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

    override fun onFragmentViewDestroyed() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_fragment_room_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_edit -> {
                val directions = RoomDetailFragmentDirections.actionRoomDetailFragmentToAddOrEditRoomFragment(viewModel.roomId)
                findNavController().navigate(directions)
                return true
            }
            R.id.action_show_plants -> {
                val directions = RoomDetailFragmentDirections.actionRoomDetailFragmentToPlantsContextualFragment(viewModel.roomId)
                findNavController().navigate(directions)
                return true
            }
            R.id.action_delete -> {
                onDeletePopup()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fillLayout(){
        binding.name.attributeText = viewModel.room.name
        viewModel.room.description?.let { binding.description.attributeText = viewModel.room.description.toString() }

        setImageView()
    }

    private fun setImageView(){
        if (viewModel.room.picture != null){
            val bitmap = PictureUtils.fromByteArrayToBitmap(viewModel.room.picture)
            val drawable = BitmapDrawable(bitmap)
            binding.roomImageContainer.setImageDrawable(drawable)
        } else{
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_room)
            binding.roomImageContainer.setImageDrawable(drawable)
        }
    }

    private fun onDeletePopup(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.deleteRoomConfirmationText))
            .setCancelable(false)
            .setPositiveButton(R.string.action_yes) { dialog, id ->
                delete()
            }
            .setNegativeButton(R.string.action_no) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun delete(){
        lifecycleScope.launch{
            viewModel.deleteRoom()
        }.invokeOnCompletion {
            finishCurrentFragment()
        }
    }
}