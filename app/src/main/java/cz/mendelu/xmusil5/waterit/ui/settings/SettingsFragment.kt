package cz.mendelu.xmusil5.waterit.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.architecture.BaseFragment
import cz.mendelu.xmusil5.waterit.databinding.FragmentSettingsBinding
import cz.mendelu.xmusil5.waterit.utils.AppLanguage
import cz.mendelu.xmusil5.waterit.utils.LanguageManager

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(SettingsViewModel::class) {


    override val bindingInflater: (LayoutInflater) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate

    override fun initViews() {
        viewModel.languageManager = LanguageManager(requireActivity())
        viewModel.loadLanguageFromPreferences()
        fillLayout()
        setInteractionListeners()

    }

    override fun onActivityCreated() {
    }

    override fun onFragmentViewDestroyed() {
    }

    private fun fillLayout(){
        binding.languagePicker.setCancelButtonVisible(false)
        var languageString: String = ""
        when(viewModel.language){
            AppLanguage.ENGLISH -> languageString = getString(R.string.language_en)
            AppLanguage.CZECH -> languageString = getString(R.string.language_cs)
        }
        binding.languagePicker.value = languageString
    }

    private fun setInteractionListeners(){
        binding.languagePicker.setRootOnClickListener(View.OnClickListener {
            launchLanguagePickWindow()
        })
    }

    private fun launchLanguagePickWindow(){
        val languages = arrayOf(getString(R.string.language_en), getString(R.string.language_cs))
        var checkedItem: Int = 0
        when(viewModel.language){
            AppLanguage.CZECH -> checkedItem = 1
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setSingleChoiceItems(languages, checkedItem, object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val selectedLanguage = languages[p1]
                    when(selectedLanguage){
                        getString(R.string.language_en) -> viewModel.saveLanguagePreference(AppLanguage.ENGLISH)
                        getString(R.string.language_cs) -> viewModel.saveLanguagePreference(AppLanguage.CZECH)
                    }
                    requireActivity().recreate()
                }

            })
            .setNegativeButton(R.string.action_cancel) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

}