package exe.weazy.memes.network

import exe.weazy.memes.entity.UserInfo

data class AuthResponse(
    val accessToken: String,
    val userInfo: UserInfo?
)