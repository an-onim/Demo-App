package ru.social.app.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName
import ru.social.demo.R

@Parcelize
data class User(
    @SerializedName("id") override var id: String = "",
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("about") val about: String? = null,
    @SerializedName("gender") val gender: Gender? = null,
    @SerializedName("birthday") val birthday: Timestamp? = null,
    @SerializedName("friends") val friends: List<String>? = null,
    @SerializedName("favGenres") val genres: List<String>? = null
) : BaseModel(), Parcelable {

    enum class Gender(val value: String, @StringRes val stringId: Int, @DrawableRes val iconId: Int) {
        @SerializedName("male") MALE("male", R.string.gender_male, R.drawable.ic_gender_male),
        @SerializedName("female") FEMALE("female", R.string.gender_female, R.drawable.ic_gender_female)
    }

}