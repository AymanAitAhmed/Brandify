package lr.aym.projet_fin_detudes.model.google

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
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
    private var signUpRequest: BeginSignInRequest,
    private val db: FirebaseFirestore
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
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isUserNew = authResult.additionalUserInfo?.isNewUser?:false
            if (isUserNew){
                addUserToFirestore()
            }
            ResponseGoogle.Success(true)
        }catch (e:Exception){
            ResponseGoogle.Failure(e)
        }
    }

    private suspend fun addUserToFirestore(){
        auth.currentUser?.apply {
            val user = toUser()
            db.collection("users").document(uid).set(user).await()
        }
    }

    fun FirebaseUser.toUser()= mapOf(
        "DISPLAY_NAME" to displayName,
        "EMAIL" to email,
        "PHOTO_URL" to photoUrl?.toString(),
        "CREATED_AT" to serverTimestamp()
    )
}