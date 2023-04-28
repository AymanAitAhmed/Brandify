package lr.aym.projet_fin_detudes.views.reset_Password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword
import lr.aym.projet_fin_detudes.model.SendPasswordResetEmailResponse
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val emailPasswordRepo: EmailPasswordAuthRepository
) : ViewModel() {

    var emailValue = mutableStateOf("")
    var showLoadingState = mutableStateOf(false)
    var resetPasswordResponse by
        mutableStateOf<SendPasswordResetEmailResponse>(ResponseEmailPassword.Success(false))

    var errorMessage = mutableStateOf("")

    var showEmailSentDialog = mutableStateOf(false)
    fun sendResetPasswordEmail() {
        viewModelScope.launch {
            resetPasswordResponse=emailPasswordRepo.sendPasswordResetEmail(email = emailValue.value)
        }
        showLoadingState.value = false
    }

}