package lr.aym.projet_fin_detudes.model.facebook

import kotlinx.coroutines.CoroutineScope

interface FacebookAuthRepository {
    fun signInWithFacebook(scope: CoroutineScope)

    fun signOutFacebookUser()
}