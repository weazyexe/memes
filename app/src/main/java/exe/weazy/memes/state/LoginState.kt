package exe.weazy.memes.state

import exe.weazy.memes.entity.UserInfo

open class LoginState {
    class Default : LoginState()
    class Error() : LoginState()
    class Success(val token: String, val userInfo: UserInfo?) : LoginState()
    class Loading : LoginState()
}