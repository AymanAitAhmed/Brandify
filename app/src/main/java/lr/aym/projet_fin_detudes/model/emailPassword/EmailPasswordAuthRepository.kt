package lr.aym.projet_fin_detudes.model.emailPassword

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import lr.aym.projet_fin_detudes.model.AuthStateResponse
import lr.aym.projet_fin_detudes.model.ReloadUserResponse
import lr.aym.projet_fin_detudes.model.RevokeAccessResponse
import lr.aym.projet_fin_detudes.model.SendEmailVerificationResponse
import lr.aym.projet_fin_detudes.model.SendPasswordResetEmailResponse
import lr.aym.projet_fin_detudes.model.SignInResponse
import lr.aym.projet_fin_detudes.model.SignUpResponse

interface EmailPasswordAuthRepository {

    val currentUser: FirebaseUser?

    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): SignUpResponse

    suspend fun sendEmailVerification(): SendEmailVerificationResponse

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): SignInResponse

    suspend fun reloadFirebaseUser(): ReloadUserResponse

    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse

    fun signOut()

    suspend fun revokeAccess(): RevokeAccessResponse

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun isUserInfoExist() : Boolean
    fun isEmailVerified(): Boolean
}