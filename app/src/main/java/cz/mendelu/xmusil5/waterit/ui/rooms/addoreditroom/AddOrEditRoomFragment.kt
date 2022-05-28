package cz.mendelu.xmusil5.waterit.ui.rooms.addoreditroom

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddOrEditRoomBinding
import cz.mendelu.xmusil5.waterit.utils.PictureUtils
import kotlinx.coroutines.launch

class AddOrEditRoomFragment : BaseFragment<FragmentAddOrEditRoomBinding, AddOrEditRoomViewModel>(AddOrEditRoomViewModel::class) {
    private val PICK_IMAGE_REQUEST_CODE = 100

    val args: AddOrEditRoomFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentAddOrEditRoomBinding
        get() = FragmentAddOrEditRoomBinding::inflate

    override fun initViews() {
        setHasOptionsMenu(true)

        setInteractionListeners()
        viewModel.roomId = args.roomId
        if (viewModel.roomId >= 0) {
            lifecycleScope.launch {
                viewModel.fetchRoom()
            }.invokeOnCompletion {
                setToolbarTitle(viewModel.room.name)
                fillLayout()
            }
        } else{
            fillLayout()
        }
    }

    override fun onActivityCreated() {
    }

    override fun onFragmentViewDestroyed() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_fragment_add_or_edit_room, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_save -> {
                onSaveAction()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





    private fun fillLayout(){
        binding.nameInput.text = viewModel.room.name

        viewModel.room.description?.let { binding.descriptionInput.text = viewModel.room.description!! }

        setImageView()
    }

    private fun onSaveAction(){
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

        binding.imagePicker.setOnPickImageListener(View.OnClickListener {
            // Checking if user gave permission to access gallery (if not, I ask for it)
            val permission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            if (permission == PackageManager.PERMISSION_GRANTED){
                launchGallery()
            } else{
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2000)
            }
        })

        binding.imagePicker.setOnCancelButtonClickListener(View.OnClickListener {
            viewModel.room.picture = null
            setImageView()
        })
    }


    private fun launchGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST_CODE){
            val imageUri = data?.data // handle chosen image
            imageUri?.let {
                propagatePickedImage(imageUri!!)
            }
        }
    }

    private fun propagatePickedImage(imageUri: Uri){
        val bitMap: Bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), Uri.parse(imageUri.toString()))
        viewModel.room.picture = PictureUtils.fromBitmapToByteArray(bitMap)
        setImageView()
    }

    private fun setImageView(){
        if (viewModel.room.picture != null){
            val bitmap = PictureUtils.fromByteArrayToBitmap(viewModel.room.picture)
            val drawable = BitmapDrawable(bitmap)
            binding.imagePicker.image = drawable
        } else{
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_room)
            binding.imagePicker.image = drawable!!
        }
    }
}