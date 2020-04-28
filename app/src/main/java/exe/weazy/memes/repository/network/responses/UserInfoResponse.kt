package exe.weazy.memes.repository.network.responses

import com.google.gson.annotations.SerializedName
import exe.weazy.memes.model.UserInfo

data class UserInfoResponse(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,

    @SerializedName("userDescription")
    val description: String
) {
    fun convert(): UserInfo = UserInfo(id, username, firstName, lastName, description)
}