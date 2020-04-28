package exe.weazy.memes.data.network.responses

data class AuthResponse(
    val accessToken: String,
    val userInfo: UserInfoResponse?
)