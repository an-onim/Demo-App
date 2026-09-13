package ru.social.app.data.model.rpg

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RpgMonster(
    @SerializedName("index") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("desc") val desc: String? = null,

    @SerializedName("charisma") val charisma: Int? = null,
    @SerializedName("constitution") val constitution: Int? = null,
    @SerializedName("dexterity") val dexterity: Int? = null,
    @SerializedName("intelligence") val intelligence: Int? = null,
    @SerializedName("strength") val strength: Int? = null,
    @SerializedName("wisdom") val wisdom: Int? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("subtype") val subtype: String? = null,
    @SerializedName("alignment") val alignment: String? = null,
//    @SerializedName("armor_class") val armorClass: List<Object>? = null,
    @SerializedName("hit_points") val hp: Int? = null,
    @SerializedName("hit_dice") val hitDice: String? = null,
    @SerializedName("hit_points_roll") val hpRoll: String? = null,

    @SerializedName("special_abilities") val abilities: List<Description>? = null
//    + many complex fields
)

@Serializable
data class Description(
    @SerializedName("name") val name: String? = null,
    @SerializedName("desc") val desc: String? = null
)

