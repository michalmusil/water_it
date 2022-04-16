package cz.mendelu.xmusil5.waterit.ui.plantdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantDetailBinding


class PlantDetailFragment : BaseFragment<FragmentPlantDetailBinding, PlantDetailViewModel>(PlantDetailViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentPlantDetailBinding
        get() = FragmentPlantDetailBinding::inflate

    override fun initViews() {

    }

    override fun onActivityCreated() {

    }


}