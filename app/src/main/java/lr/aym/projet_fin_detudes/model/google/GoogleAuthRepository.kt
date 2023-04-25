package lr.aym.projet_fin_detudes.model.google

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential

typealias OneTapSignInResponse = ResponseGoogle<BeginSignInResult>
typealias SignInWithGoogleResponse = ResponseGoogle<Boolean>

interface GoogleAuthRepository {
    val isUserAuthenticatedInFirebase: Boolean

    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse
}
