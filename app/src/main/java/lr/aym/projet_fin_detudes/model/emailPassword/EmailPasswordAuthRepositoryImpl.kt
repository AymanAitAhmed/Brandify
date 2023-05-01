package lr.aym.projet_fin_detudes.model.emailPassword

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import lr.aym.projet_fin_detudes.model.ReloadUserResponse
import lr.aym.projet_fin_detudes.model.RevokeAccessResponse
import lr.aym.projet_fin_detudes.model.SendEmailVerificationResponse
import lr.aym.projet_fin_detudes.model.SendPasswordResetEmailResponse
import lr.aym.projet_fin_detudes.model.SignInResponse
import lr.aym.projet_fin_detudes.model.SignUpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailPasswordAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : EmailPasswordAuthRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser


    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String,
        password: String
    ): SignUpResponse {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            //Log.d("signuptag", "Repo createUser success")
            ResponseEmailPassword.Success(true)
        } catch (e: Exception) {
            //Log.d("signuptag", "$e")
            ResponseEmailPassword.Failure(e)
        }
    }

    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            //Log.d("signuptag", "SignUp email sent")
            ResponseEmailPassword.Success(true)
        } catch (e: Exception) {
            ResponseEmailPassword.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): SignInResponse {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            ResponseEmailPassword.Success(true)
        } catch (e: Exception) {
            ResponseEmailPassword.Failure(e)
        }
    }

    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            /*Log.d(
                "isEmailVerified",
                "reloadFirebaseUser: user reloaded ${auth.currentUser?.isEmailVerified}"
            )*/
            ResponseEmailPassword.Success(true)
        } catch (e: Exception) {
            //Log.d("isEmailVerified", "reloadFirebaseUser Failure: ${e}")
            ResponseEmailPassword.Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        return try {
            auth.sendPasswordResetEmail(email).await()
            ResponseEmailPassword.Success(true)
        } catch (e: Exception) {
            ResponseEmailPassword.Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.delete()?.await()
            ResponseEmailPassword.Success(true)
        } catch (e: Exception) {
            ResponseEmailPassword.Failure(e)
        }
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}