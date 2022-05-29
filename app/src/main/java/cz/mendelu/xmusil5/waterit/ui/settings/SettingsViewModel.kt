package cz.mendelu.xmusil5.waterit.ui.settings

import androidx.lifecycle.ViewModel
import cz.mendelu.xmusil5.waterit.utils.AppLanguage
import cz.mendelu.xmusil5.waterit.utils.LanguageManager

class SettingsViewModel(private val languageManager: LanguageManager): ViewModel() {

    lateinit var language: AppLanguage


    fun loadLanguageFromPreferences(){
        language = languageManager.getLanguagePreference()
    }

    fun saveLanguagePreference(lang: AppLanguage){
        languageManager.updateAppLanguage(lang)
    }
}