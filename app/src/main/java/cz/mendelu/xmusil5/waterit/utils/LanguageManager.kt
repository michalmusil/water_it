package cz.mendelu.xmusil5.waterit.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class LanguageManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("LANG", Context.MODE_PRIVATE)

    fun updateAppLanguage(language: AppLanguage){
        val locale = Locale(language.languageCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        saveLanguagePreference(language)
    }

    fun getLanguagePreference(): AppLanguage{
        val default = Locale.getDefault().language
        var code: String?
        if (default == "cs"){
            code = sharedPreferences.getString("lang", default)
        } else{
            code = sharedPreferences.getString("lang", "en")
        }

        return AppLanguage.appLanguageByCode(code!!)
    }

    private fun saveLanguagePreference(language: AppLanguage){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("lang", language.languageCode)
        editor.commit()
    }
}