package lr.aym.projet_fin_detudes.views.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    var passwordTextFieldValue = mutableStateOf("")
    var confirmPasswordTextFieldValue = mutableStateOf("")

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
    var emailErrorMessage = mutableStateOf("")
    var passwordErrorMessage = mutableStateOf("")
    var confirmErrorMessage = mutableStateOf("")

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
            emailErrorMessage.value = "Please enter your email"
            false
        }else if (!emailTextFieldValue.value.contains("@")){
            emailErrorMessage.value = "Please enter a valid email"
            false
        }else{
            emailErrorMessage.value = ""
            true
        }
    }
    fun passwordChecks() :Boolean {

        if (passwordTextFieldValue.value.length < 8) {
            passwordErrorMessage.value = "Password must have 8 charachters at least"
            return false
        } else
            if (!passwordTextFieldValue.value.any { it.isDigit() } ||
                !passwordTextFieldValue.value.any { it.isLetter() }
            ) {
                passwordErrorMessage.value = "Password must have at least one character and one digit"
                return false
            } else if (passwordTextFieldValue.value != confirmPasswordTextFieldValue.value) {
                passwordErrorMessage.value = ""
                confirmErrorMessage.value = "Passwords do not match"
                return false
            } else {
                passwordErrorMessage.value = ""
                confirmErrorMessage.value = ""
                return true
            }
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponseEmailPassword = ResponseEmailPassword.Loading
        sendEmailVerificationResponseEmailPassword = repository.sendEmailVerification()
    }


}