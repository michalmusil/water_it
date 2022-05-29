package cz.mendelu.xmusil5.waterit.utils

import java.lang.IllegalArgumentException

enum class AppLanguage(val languageCode: String) {
    ENGLISH("en"),
    CZECH("cs");

    companion object {
        fun appLanguageByCode(languageCode: String): AppLanguage{
            val type = AppLanguage.values().find{it.languageCode == languageCode}
            if (type == null) {throw IllegalArgumentException()
            }
            return type
        }
    }
}