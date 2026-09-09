package com.estacionamiento.inteligente.data.repository

import android.content.Context
import android.content.SharedPreferences

class PreferenceRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("smart_parking_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_GEMINI_API_KEY = "gemini_api_key"

        @Volatile
        private var INSTANCE: PreferenceRepository? = null

        fun getInstance(context: Context): PreferenceRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferenceRepository(context).also { INSTANCE = it }
            }
        }
    }

    fun getGeminiApiKey(): String {
        return prefs.getString(KEY_GEMINI_API_KEY, "AQ.Ab8RN6J9PWpYi8k1sLBmEqWuAnBxLVsZMlUH1ap8h2xtb_91tQ") ?: "AQ.Ab8RN6J9PWpYi8k1sLBmEqWuAnBxLVsZMlUH1ap8h2xtb_91tQ"
    }

    fun saveGeminiApiKey(apiKey: String) {
        prefs.edit().putString(KEY_GEMINI_API_KEY, apiKey).apply()
    }
}
