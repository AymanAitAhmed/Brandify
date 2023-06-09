package lr.aym.projet_fin_detudes.model.google

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue.serverTimestamp
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class GoogleAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named("SIGN_IN_REQUEST")
    private var signInRequest: BeginSignInRequest,
    @Named("SIGN_UP_REQUEST")
    private var signUpRequest: BeginSignInRequest
) : GoogleAuthRepository {
    override val isUserAuthenticatedInFirebase: Boolean
        get() = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            ResponseGoogle.Success(signInResult)
        }catch (e:Exception){
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                ResponseGoogle.Success(signUpResult)
            }catch (e:Exception){
                ResponseGoogle.Failure(e)
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse {
        return try {
            auth.signInWithCredential(googleCredential).await()
            ResponseGoogle.Success(true)
        }catch (e:Exception){
            ResponseGoogle.Failure(e)
        }
    }
}