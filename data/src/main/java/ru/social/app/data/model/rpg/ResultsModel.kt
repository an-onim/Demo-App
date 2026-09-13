package ru.social.app.data.model.rpg

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResultsModel(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("results") val results: List<ShortInfo>? = null
)