package exe.weazy.memes.entity

import com.google.gson.annotations.SerializedName
import exe.weazy.memes.util.values.EMPTY_INT
import exe.weazy.memes.util.values.EMPTY_STRING

data class UserInfo(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("userDescription")
    val description: String
) {
    companion object {
        fun Empty() = UserInfo(
            id = EMPTY_INT,
            username = EMPTY_STRING,
            firstName = EMPTY_STRING,
            lastName = EMPTY_STRING,
            description = EMPTY_STRING
        )
    }
}