package cz.mendelu.xmusil5.waterit.ui.plants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentPlantsBinding


class PlantsFragment : BaseFragment<FragmentPlantsBinding, PlantsViewModel>(PlantsViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> FragmentPlantsBinding
        get() = FragmentPlantsBinding::inflate

    override fun initViews() {

    }

    override fun onActivityCreated() {

    }
}