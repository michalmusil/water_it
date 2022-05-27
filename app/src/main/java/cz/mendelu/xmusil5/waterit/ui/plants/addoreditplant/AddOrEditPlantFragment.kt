package cz.mendelu.xmusil5.waterit.ui.plants.addoreditplant

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
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.database.entities.DbRoom
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddOrEditPlantBinding
import cz.mendelu.xmusil5.waterit.ui.dialogfragments.rooms.RoomsDialogFragment
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import cz.mendelu.xmusil5.waterit.utils.PictureUtils
import cz.mendelu.xmusil5.waterit.views.DatePickerView
import kotlinx.coroutines.launch


class AddOrEditPlantFragment : BaseFragment<FragmentAddOrEditPlantBinding, AddOrEditPlantViewModel>(AddOrEditPlantViewModel::class) {

    private val PICK_IMAGE_REQUEST_CODE = 100
    private val args: AddOrEditPlantFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentAddOrEditPlantBinding
        get() = FragmentAddOrEditPlantBinding::inflate

    override fun initViews() {
        viewModel.plantId = args.plantId
        if (viewModel.plantId >= 0){
            lifecycleScope.launch{
                viewModel.fetchPlant()
            }.invokeOnCompletion {
                setToolbarTitle(viewModel.plantWithRoom.plant.name)
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

    override fun onFragmentViewDestroyed() {
    }

    private fun fillLayout(){
        binding.nameInput.text = viewModel.plantWithRoom.plant.name
        binding.speciesInput.text = viewModel.plantWithRoom.plant.species

        viewModel.plantWithRoom.room?.name?.let { binding.room.value = viewModel.plantWithRoom.room!!.name }
        viewModel.plantWithRoom.plant.dateOfPlanting?.let { binding.dateOfPlanting.datePickText = DateUtils.getDateString(viewModel.plantWithRoom.plant.dateOfPlanting!!) }
        viewModel.plantWithRoom.plant.lastWatered?.let { binding.lastWatered.datePickText = DateUtils.getDateString(viewModel.plantWithRoom.plant.lastWatered!!) }
        viewModel.plantWithRoom.plant.description?.let { binding.descriptionInput.text = viewModel.plantWithRoom.plant.description!! }
        viewModel.plantWithRoom.plant.daysBetweenWatering?.let { binding.daysBetweenWateringInput.number = viewModel.plantWithRoom.plant.daysBetweenWatering!! }

        setImageView()
        setRoomImageView()
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
        binding.daysBetweenWateringInput.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(number: Editable?) {
                val doubleValue = number.toString().toDoubleOrNull()
                doubleValue?.let { viewModel.plantWithRoom.plant.daysBetweenWatering = doubleValue.toInt() }
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

        binding.lastWatered.setOnDateChangedListener(object: DatePickerView.CustomOnDateChangedListener{
            override fun onDateChanged(unixTime: Long?) {
                viewModel.plantWithRoom.plant.lastWatered = unixTime
            }
        })

        binding.room.setRootOnClickListener(View.OnClickListener {
            var dialog = RoomsDialogFragment(object : RoomsDialogFragment.RoomOnClickListener{
                override fun onRoomClicked(room: DbRoom) {
                    viewModel.plantWithRoom.room = room
                    viewModel.plantWithRoom.plant.roomId = room.id
                    binding.room.value = room.name
                    setRoomImageView()
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "rooms")
        })

        binding.room.setOnCancelButtonListener(View.OnClickListener {
            viewModel.plantWithRoom.room = null
            viewModel.plantWithRoom.plant.roomId = null
            binding.room.value = ""
            setRoomImageView()
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
            viewModel.plantWithRoom.plant.picture = null
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
        viewModel.plantWithRoom.plant.picture = PictureUtils.fromBitmapToByteArray(bitMap)
        setImageView()
    }

    private fun setImageView(){
        if (viewModel.plantWithRoom.plant.picture != null){
            val bitmap = PictureUtils.fromByteArrayToBitmap(viewModel.plantWithRoom.plant.picture)
            val drawable = BitmapDrawable(bitmap)
            binding.imagePicker.image = drawable
        } else{
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_local_florist_24)
            binding.imagePicker.image = drawable!!
        }
    }

    private fun setRoomImageView(){
        if (viewModel.plantWithRoom.room != null && viewModel.plantWithRoom.room!!.picture != null) {
            val bitmap = PictureUtils.fromByteArrayToBitmap(viewModel.plantWithRoom.room!!.picture)
            val drawable = BitmapDrawable(bitmap)
            binding.room.setImageDrawable(drawable)
        } else {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_meeting_room_24)
            binding.room.setImageDrawable(drawable!!)
        }
    }
}