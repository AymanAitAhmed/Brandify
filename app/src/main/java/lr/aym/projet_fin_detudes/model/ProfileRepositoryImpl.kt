package lr.aym.projet_fin_detudes.model

import android.util.Log
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
    class ProfileRepositoryImpl @Inject constructor(
private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient
): ProfileRepository {
    override val displayName: String
        get() = auth.currentUser?.displayName.toString()
    override val photoUrl: String
        get() = auth.currentUser?.photoUrl.toString()

    override fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut()
            auth.signOut()
            ResponseGoogle.Success(true)
        }catch (e:Exception){
            Log.d("signoutFail", "signOut: $e")
            ResponseGoogle.Failure(e)
        }
    }
}