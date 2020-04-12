package exe.weazy.memes.network.responses

data class AuthResponse(
    val accessToken: String,
    val userInfo: UserInfoResponse?
)