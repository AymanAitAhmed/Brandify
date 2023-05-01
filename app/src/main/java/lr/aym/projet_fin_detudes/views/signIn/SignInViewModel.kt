package lr.aym.projet_fin_detudes.views.signIn


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.google.GoogleAuthRepository
import lr.aym.projet_fin_detudes.model.google.OneTapSignInResponse
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import lr.aym.projet_fin_detudes.model.SignInResponse
import lr.aym.projet_fin_detudes.model.google.SignInWithGoogleResponse
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val emailPasswordRepo: EmailPasswordAuthRepository,
    private val googleRepo: GoogleAuthRepository,
    val onTapClient : SignInClient
) : ViewModel() {

    var emailTextFieldValue = mutableStateOf("")

    var passwordTextFieldValue = mutableStateOf("")

    var showHidePassword = mutableStateOf(false)

    var emailAndPasswordSignInResponse by mutableStateOf<SignInResponse>(ResponseEmailPassword.Success(false))

    var onTapGoogleSignInResponse by mutableStateOf<OneTapSignInResponse>(ResponseGoogle.Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(ResponseGoogle.Success(false))
    var showLoadingState = mutableStateOf(false)

    var errorMessage = mutableStateOf("")

    var showFacebookLinkAccountDialog = mutableStateOf(false)
    var signInWithGoogle = mutableStateOf(false)



    fun onShowHideEyeClick() {
        showHidePassword.value = !showHidePassword.value
    }

    suspend fun getEmailVerfiedState():Boolean{
        return suspendCoroutine {continuation ->
            val isEmailVerified = emailPasswordRepo.currentUser?.isEmailVerified?:false
            //Log.d("isEmailVerified", "getEmailVerfiedState: $isEmailVerified")
            continuation.resume(isEmailVerified)
        }
    }


    fun onSignInWithEmailAndPasswordClick() = viewModelScope.launch {
        emailAndPasswordSignInResponse = ResponseEmailPassword.Loading
        emailAndPasswordSignInResponse = emailPasswordRepo.firebaseSignInWithEmailAndPassword(
            emailTextFieldValue.value,
            passwordTextFieldValue.value
        )

    }

    fun reloadUser(){
        viewModelScope.launch {
          emailPasswordRepo.reloadFirebaseUser()
        }
    }

    fun onSignInWithGoogleClick()=viewModelScope.launch {
        onTapGoogleSignInResponse = ResponseGoogle.Loading
        onTapGoogleSignInResponse = googleRepo.oneTapSignInWithGoogle()
    }

    fun signInWithGoogle(googleCredentials : AuthCredential)=viewModelScope.launch {
        onTapGoogleSignInResponse = ResponseGoogle.Loading
        signInWithGoogleResponse = googleRepo.firebaseSignInWithGoogle(googleCredentials)
    }


}