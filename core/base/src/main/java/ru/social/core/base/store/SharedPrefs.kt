package ru.social.core.base.store

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefs @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs = context.getSharedPreferences("ru.social.demo", Context.MODE_PRIVATE)

    fun getUserId() = prefs.getString(USER_ID, null)
    fun setUserId(id: String?) = prefs.edit().putString(USER_ID, id).apply()
    fun clearUserId() = prefs.edit().remove(USER_ID).apply()

    fun isHost() = prefs.getBoolean(HOST_MODE, false)
    fun setIsHost(value: Boolean) = prefs.edit().putBoolean(HOST_MODE, value).apply()

    companion object {
        const val USER_ID = "userId"
        const val HOST_MODE = "hostMode"
    }

}