package com.innowi.android.util.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.innowi.android.base.BaseApplication
import com.innowi.android.domain.model.photo.response.PhotoResponse

object MySharePreference {

    private const val SAVE_DATA = "save_data"

    private val pref = BaseApplication.getInstance().getSharedPreferences("app", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()


    fun saveData(data: ArrayList<PhotoResponse>) {
        editor.putString(SAVE_DATA, Gson().toJson(data))
        editor.commit()
    }

    fun getSavedData(): String? {
        return pref.getString(SAVE_DATA, "")
    }
}