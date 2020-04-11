package exe.weazy.memes.entity

import com.google.gson.annotations.SerializedName

data class UserInfo(
    val id: Int, val username: String, val firstName: String,
    val lastName: String,

    @SerializedName("userDescription")
    val description: String
)