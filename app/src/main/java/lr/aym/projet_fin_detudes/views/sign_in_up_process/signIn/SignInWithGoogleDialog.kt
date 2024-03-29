package lr.aym.projet_fin_detudes.views.sign_in_up_process.signIn

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import lr.aym.projet_fin_detudes.components.Screens
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle


@Composable
fun GoogleSignIn(
    viewModel: SignInViewModel,
    navController: NavController
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credentials =
                        viewModel.onTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                    viewModel.signInWithGoogle(googleCredentials)
                } catch (it: ApiException) {
                    print(it)
                }
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    when (val oneTapSignInResponse = viewModel.onTapGoogleSignInResponse) {
        is ResponseGoogle.Loading -> {
            viewModel.showLoadingState.value = true
            Log.d("checkSignIN", "Loading:Google OneTapResponse ")
        }

        is ResponseGoogle.Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                Log.d("checkSignIN", "Success:Google OneTapResponse ")
                launch(it)
            }
        }

        is ResponseGoogle.Failure -> LaunchedEffect(Unit) {
            viewModel.showLoadingState.value = false
            Log.d("checkSignIN", "Failure:Google OneTapResponse ${oneTapSignInResponse.e.message}")
        }
    }

    when (val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is ResponseGoogle.Loading -> {
            Log.d("checkSignIN", "Loading:signInGoogleResponse ")
        }

        is ResponseGoogle.Success -> signInWithGoogleResponse.data?.let { signedIn ->
            viewModel.showLoadingState.value = false
            LaunchedEffect(signedIn) {
                if (signedIn) {
                    Log.d("checkSignIN", "Success:signInGoogleResponse ")
                    //Log.d("navigatedFrom", "Sign In google")
                    navController.navigate(Screens.HomeScreen.route){
                        popUpTo(route = Screens.SignInScreen.route){
                            inclusive = true
                        }
                    }
                }
            }
        }

        is ResponseGoogle.Failure -> LaunchedEffect(Unit) {
            viewModel.showLoadingState.value = false
            Log.d(
                "checkSignIN",
                "Failure:signInGoogleResponse ${signInWithGoogleResponse.e.message} "
            )
        }
    }
}