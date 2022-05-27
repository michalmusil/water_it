package cz.mendelu.xmusil5.waterit.ui.plants.plantdetail

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantDetailBinding
import cz.mendelu.xmusil5.waterit.utils.DateUtils
import cz.mendelu.xmusil5.waterit.utils.PictureUtils
import kotlinx.coroutines.launch


class PlantDetailFragment : BaseFragment<FragmentPlantDetailBinding, PlantDetailViewModel>(PlantDetailViewModel::class) {

    private val args: PlantDetailFragmentArgs by navArgs()


    override val bindingInflater: (LayoutInflater) -> FragmentPlantDetailBinding
        get() = FragmentPlantDetailBinding::inflate

    override fun initViews() {
        setHasOptionsMenu(true)

        viewModel.plantId = this.args.plantId
        if (viewModel.plantId>=0){
            lifecycleScope.launch {
                viewModel.fetchPlant()
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
        requireActivity().menuInflater.inflate(R.menu.menu_fragment_plant_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_edit -> {
                val directions = PlantDetailFragmentDirections.actionPlantDetailFragmentToAddOrEditPlantFragment(viewModel.plantId)
                findNavController().navigate(directions)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }






    private fun fillLayout(){
        binding.name.attributeText = viewModel.plantWithRoom.plant.name
        binding.species.attributeText = viewModel.plantWithRoom.plant.species
        viewModel.plantWithRoom.room?.let { binding.room.attributeText = viewModel.plantWithRoom.room!!.name }
        viewModel.plantWithRoom.plant.dateOfPlanting?.let { binding.datePlanted.attributeText = DateUtils.getDateString(viewModel.plantWithRoom.plant.dateOfPlanting!!) }
        viewModel.plantWithRoom.plant.lastWatered?.let { binding.lastWatered.attributeText = DateUtils.getDateString(viewModel.plantWithRoom.plant.lastWatered!!) }
        viewModel.plantWithRoom.plant.daysBetweenWatering?.let { binding.daysBetweenWatering.attributeText = viewModel.plantWithRoom.plant.daysBetweenWatering.toString() }
        viewModel.plantWithRoom.plant.description?.let { binding.description.attributeText = viewModel.plantWithRoom.plant.description.toString() }

        setImageView()
    }

    private fun setImageView(){
        if (viewModel.plantWithRoom.plant.picture != null){
            val bitmap = PictureUtils.fromByteArrayToBitmap(viewModel.plantWithRoom.plant.picture)
            val drawable = BitmapDrawable(bitmap)
            binding.plantImageContainer.setImageDrawable(drawable)
        } else{
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_local_florist_24)
            binding.plantImageContainer.setImageDrawable(drawable)
        }
    }

}