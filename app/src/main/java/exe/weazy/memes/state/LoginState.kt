package exe.weazy.memes.state

import exe.weazy.memes.entity.UserInfo

open class LoginState {
    class Default : LoginState()
    class Error() : LoginState()
    class Success(token: String, userInfo: UserInfo?) : LoginState()
    class Loading : LoginState()
}