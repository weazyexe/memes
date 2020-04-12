package exe.weazy.memes.entity

import exe.weazy.memes.util.values.EMPTY_INT
import exe.weazy.memes.util.values.EMPTY_STRING

data class UserInfo(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val description: String
) {
    companion object {
        fun empty() = UserInfo(
            id = EMPTY_INT,
            username = EMPTY_STRING,
            firstName = EMPTY_STRING,
            lastName = EMPTY_STRING,
            description = EMPTY_STRING
        )
    }
}