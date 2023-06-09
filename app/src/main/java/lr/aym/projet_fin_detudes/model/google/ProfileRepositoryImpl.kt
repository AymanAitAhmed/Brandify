package lr.aym.projet_fin_detudes.model.google

import android.util.Log
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient
) : ProfileRepository {


    override fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut()
            auth.signOut()
            ResponseGoogle.Success(true)
        } catch (e: Exception) {
            Log.d("signoutFail", "signOut: $e")
            ResponseGoogle.Failure(e)
        }
    }

}