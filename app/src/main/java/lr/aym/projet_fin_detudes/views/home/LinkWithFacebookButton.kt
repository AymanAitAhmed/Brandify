package lr.aym.projet_fin_detudes.views.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.R

@Composable
fun LinkWithFacebookButton(
    onLinkSuccesfful: () -> Unit,
    onLinkError: (Exception) -> Unit
) {

    val scope = rememberCoroutineScope()
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {
        // nothing to do. handled in FacebookCallback
    }

    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
                onLinkError(Exception("Account linking process canceled, try again later."))
            }

            override fun onError(error: FacebookException) {
                onLinkError(error)
            }

            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    try {
                        val token = result.accessToken.token
                        val credential = FacebookAuthProvider.getCredential(token)
                        val linkResult = Firebase.auth.currentUser?.linkWithCredential(credential)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onLinkSuccesfful()
                                }
                                else{
                                    task.exception?.let { onLinkError(it) }
                                }
                            }

                    } catch (e: Exception) {
                        onLinkError(e)
                    }

                }
            }
        })

        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 4.dp, end = 4.dp)
            .clickable {
                launcher.launch(listOf("email", "public_profile"))
                       },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.facebook_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(30.dp),
            tint = MaterialTheme.colors.primary
        )
        Text(
            text = stringResource(id = R.string.link_with_facebook),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

    }
    Divider(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(end = 16.dp),
        color = MaterialTheme.colors.onSecondary
    )

}