package exe.weazy.memes.repository.network.responses

data class AuthResponse(
    val accessToken: String,
    val userInfo: UserInfoResponse?
)