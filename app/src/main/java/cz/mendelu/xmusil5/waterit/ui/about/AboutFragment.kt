package cz.mendelu.xmusil5.waterit.ui.about

import android.view.LayoutInflater
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>(AboutViewModel::class) {
    override val bindingInflater: (LayoutInflater) -> FragmentAboutBinding
        get() = FragmentAboutBinding::inflate

    override fun initViews() {
        val version = requireContext().packageManager
            .getPackageInfo(requireContext().packageName, 0)
        val versionString = getString(R.string.version)
        binding.versionInformation.text = "${versionString}: ${version.versionName}"
    }

    override fun onActivityCreated() {

    }

    override fun onFragmentViewDestroyed() {

    }

}