package lr.aym.projet_fin_detudes.views.sign_in_up_process.verifyEmail


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.SignOut
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import lr.aym.projet_fin_detudes.model.google.SignOutResponse
import javax.inject.Inject

@HiltViewModel
class VerifiyEmailViewModel @Inject constructor(
    private val emailPasswordAuthRepository: EmailPasswordAuthRepository,
    private val signOut: SignOut
) : ViewModel() {

    val currentUser = emailPasswordAuthRepository.currentUser

    var showLoadingStateContinue = mutableStateOf(false)

    var verifiedEmail by mutableStateOf(false)

    var showErrorMessage by mutableStateOf(false)

    var errorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))

    var facebookProvider = mutableStateOf(false)

    var signOutResponse by mutableStateOf<SignOutResponse>(ResponseGoogle.Success(false))

    fun isEmailVerified() {
        showLoadingStateContinue.value = true
        viewModelScope.launch {
            emailPasswordAuthRepository.reloadFirebaseUser()
            verifiedEmail = emailPasswordAuthRepository.currentUser?.isEmailVerified ?: false
            if (!verifiedEmail){
                showErrorMessage = true
                errorMessage.value = UiText.StringResource(R.string.error_verify_email)
            }
            //Log.d("isEmailVerified", "isEmailVerified $verifiedEmail")
            showLoadingStateContinue.value = false
        }
    }

    fun resendVerificationEmail() {
        showLoadingStateContinue.value = true
        viewModelScope.launch {
            emailPasswordAuthRepository.sendEmailVerification()
            showLoadingStateContinue.value = false
        }
    }

    fun signOut() {
        signOutResponse = signOut.signOut()
    }


}