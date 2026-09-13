package ru.social.app.data.model.rpg

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Spellcasting(
    @SerializedName("level") val level: Int? = null,

    @SerializedName("info") val info: List<DescriptionList>? = null,
    @SerializedName("spellcasting_ability ") val ability: AbilityScore? = null
)

@Serializable
data class DescriptionList(
    @SerializedName("name") val name: String? = null,
    @SerializedName("desc") val desc: List<String>? = null
)

@Serializable
data class AbilityScore(
    @SerializedName("index") val id: String? = null,
    @SerializedName("level") val level: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)
