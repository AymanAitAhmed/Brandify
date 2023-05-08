package lr.aym.projet_fin_detudes.views.sign_in_up_process.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword
import lr.aym.projet_fin_detudes.model.SendEmailVerificationResponse
import lr.aym.projet_fin_detudes.model.SignUpResponse
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: EmailPasswordAuthRepository
) : ViewModel() {

    var emailTextFieldValue = mutableStateOf("")
    var emailErrorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))
    var passwordTextFieldValue = mutableStateOf("")
    var passwordErrorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))
    var confirmPasswordTextFieldValue = mutableStateOf("")
    var confirmErrorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))

    var signUpResponseEmailPassword by mutableStateOf<SignUpResponse>(
        ResponseEmailPassword.Success(
            false
        )
    )
        private set
    var sendEmailVerificationResponseEmailPassword by mutableStateOf<SendEmailVerificationResponse>(
        ResponseEmailPassword.Success(
            false
        )
    )
        private set

    var showHidePassword = mutableStateOf(false)
    var showLoadingState = mutableStateOf(false)
    var errorMessage = mutableStateOf("")


    fun onShowHideEyeClick() {
        showHidePassword.value = !showHidePassword.value
    }

    fun onSignUpWithEmailAndPassword() = viewModelScope.launch {
        signUpResponseEmailPassword = ResponseEmailPassword.Loading
        signUpResponseEmailPassword = repository.firebaseSignUpWithEmailAndPassword(
            emailTextFieldValue.value,
            passwordTextFieldValue.value
        )
    }

    fun emailChecks():Boolean{
        return if (emailTextFieldValue.value.isBlank()){
            emailErrorMessage.value = UiText.StringResource(R.string.empty_email)
            false
        }else if (!emailTextFieldValue.value.contains("@")){
            emailErrorMessage.value = UiText.StringResource(R.string.non_valid_email)
            false
        }else{
            emailErrorMessage.value = UiText.StringResource(R.string.empty_string)
            true
        }
    }
    fun passwordChecks() :Boolean {

        if (passwordTextFieldValue.value.length < 8) {
            passwordErrorMessage.value = UiText.StringResource(R.string.small_password_length)
            return false
        } else
            if (!passwordTextFieldValue.value.any { it.isDigit() } ||
                !passwordTextFieldValue.value.any { it.isLetter() }
            ) {
                passwordErrorMessage.value = UiText.StringResource(R.string.password_no_char_digit)
                return false
            } else if (passwordTextFieldValue.value != confirmPasswordTextFieldValue.value) {
                passwordErrorMessage.value = UiText.StringResource(R.string.empty_string)
                confirmErrorMessage.value = UiText.StringResource(R.string.mismatch_passwords)
                return false
            } else {
                passwordErrorMessage.value = UiText.StringResource(R.string.empty_string)
                confirmErrorMessage.value = UiText.StringResource(R.string.empty_string)
                return true
            }
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponseEmailPassword = ResponseEmailPassword.Loading
        sendEmailVerificationResponseEmailPassword = repository.sendEmailVerification()
    }


}