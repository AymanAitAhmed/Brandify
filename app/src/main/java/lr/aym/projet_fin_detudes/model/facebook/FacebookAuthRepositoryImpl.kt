package lr.aym.projet_fin_detudes.model.facebook

import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FacebookAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val loginManager: LoginManager,
    private val callbackManager: CallbackManager
) : FacebookAuthRepository {


    override fun signInWithFacebook(scope: CoroutineScope) {

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
            }

            override fun onError(error: FacebookException) {
                // do nothing
            }

            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    try {
                        val token = result.accessToken.token
                        val credential = FacebookAuthProvider.getCredential(token)
                        auth.signInWithCredential(credential).await()

                    } catch (e: Exception) {
                        //Log.d("fbsignin", "failed ${e}")

                    }
                }
            }

        })


    }

    override fun signOutFacebookUser() {
        loginManager.logOut()
        auth.signOut()
    }


}