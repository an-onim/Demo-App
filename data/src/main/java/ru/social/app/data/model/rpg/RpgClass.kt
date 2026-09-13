package ru.social.app.data.model.rpg

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RpgClass(
    @SerializedName("index") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("hit_die") val hitDie: Int? = null,
    @SerializedName("class_levels") val classLevels: String? = null,
    @SerializedName("spells") val spells: String? = null,
//    @SerializedName("multi_classing") val multiclassing: Multiclassing? = null,
    @SerializedName("spellcasting") val spellcasting: Spellcasting? = null,
//    @SerializedName("starting_equipment") val equipment: Pair<Int, ShortInfo>? = null,
//    @SerializedName("starting_equipment_options") val equipmentOptions: List<Object1>? = null,
//    @SerializedName("proficiency_choices") val proficiencyChoices: List<Object1>? = null,
    @SerializedName("proficiencies") val proficiencies: List<ShortInfo>? = null,
    @SerializedName("saving_throws") val savingThrows: List<ShortInfo>? = null,
    @SerializedName("subclasses") val subclasses: List<ShortInfo>? = null
)
