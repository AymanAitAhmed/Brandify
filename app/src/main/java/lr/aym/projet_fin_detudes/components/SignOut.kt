package lr.aym.projet_fin_detudes.components

import lr.aym.projet_fin_detudes.model.google.ProfileRepository
import lr.aym.projet_fin_detudes.model.facebook.FacebookAuthRepository
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import lr.aym.projet_fin_detudes.model.google.SignOutResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignOut @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val facebookAuthRepo: FacebookAuthRepository
) {
    fun signOut(): SignOutResponse {
        return try {
            profileRepository.signOut()
            facebookAuthRepo.signOutFacebookUser()
            ResponseGoogle.Success(true)
        }catch (e:Exception){
            ResponseGoogle.Failure(e)
        }

    }
}