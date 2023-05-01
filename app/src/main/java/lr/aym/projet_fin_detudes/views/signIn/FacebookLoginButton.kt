package lr.aym.projet_fin_detudes.views.signIn

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import lr.aym.projet_fin_detudes.R

@Composable
fun FacebookLoginButton(
    onAuthComplete: () -> Unit,
    onAuthError: (Exception) -> Unit,
    modifier: Modifier = Modifier,
    ) {
        val scope = rememberCoroutineScope()
        val loginManager = LoginManager.getInstance()
        val callbackManager = remember { CallbackManager.Factory.create() }
        val launcher = rememberLauncherForActivityResult(
            loginManager.createLogInActivityResultContract(callbackManager, null)) {
            // nothing to do. handled in FacebookCallback
        }

        DisposableEffect(Unit) {
            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    // do nothing
                }

                override fun onError(error: FacebookException) {
                    onAuthError(error)
                }

                override fun onSuccess(result: LoginResult) {
                    scope.launch {
                        try {
                            val token = result.accessToken.token
                            val credential = FacebookAuthProvider.getCredential(token)
                            val authResult = Firebase.auth.signInWithCredential(credential).await()

                            if (authResult.user != null) {
                                onAuthComplete()
                            } else {
                                onAuthError(IllegalStateException("Unable to sign in with Facebook"))
                            }
                        }catch (e :Exception){
                            onAuthError(e)
                        }

                    }
                }
            })

            onDispose {
                loginManager.unregisterCallback(callbackManager)
            }
        }
    IconButton(onClick = {
        launcher.launch(listOf("email", "public_profile"))
    }) {
        Icon(
            painter = painterResource(id = R.drawable.facebook_logo),
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(8.dp)
                .size(45.dp)
        )
    }

}