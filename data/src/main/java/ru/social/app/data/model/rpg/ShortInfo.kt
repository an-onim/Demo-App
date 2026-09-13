package ru.social.app.data.model.rpg

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ShortInfo(
    @SerializedName("index") val id: String? = null,
    @SerializedName("level") val level: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)
