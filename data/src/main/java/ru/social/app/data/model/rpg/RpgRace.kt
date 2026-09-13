package ru.social.app.data.model.rpg

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RpgRace(
    @SerializedName("index") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("speed") val speed: Int? = null,
//    @SerializedName("ability_bonuses") val abilityBonuses : List<Bonus>? = null,
    @SerializedName("alignment") val alignment: String? = null,
    @SerializedName("age") val age: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("size_description") val sizeDesc: String? = null,
//    @SerializedName("starting_proficiencies") val proficiencies: List<ShortInfo>? = null,
//    @SerializedName("starting_proficiency_options") val proficiencyOptions: Choice? = null,
    @SerializedName("languages") val languages: List<ShortInfo>? = null,
    @SerializedName("language_desc") val languageDesc : String? = null,
    @SerializedName("traits") val traits: List<ShortInfo>? = null,
    @SerializedName("subraces") val subraces: List<ShortInfo>? = null
)


