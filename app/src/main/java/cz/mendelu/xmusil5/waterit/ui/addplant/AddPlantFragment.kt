package cz.mendelu.xmusil5.waterit.ui.addplant

import android.view.LayoutInflater
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentAddPlantBinding


class AddPlantFragment : BaseFragment<FragmentAddPlantBinding, AddPlantViewModel>(AddPlantViewModel::class) {


    override val bindingInflater: (LayoutInflater) -> FragmentAddPlantBinding
        get() = FragmentAddPlantBinding::inflate

    override fun initViews() {

    }

    override fun onActivityCreated() {

    }


}