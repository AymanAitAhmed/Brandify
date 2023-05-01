package lr.aym.projet_fin_detudes.views.signIn

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.components.authErrors
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword

@Composable
fun EmailPasswordSignIn(
    viewModel: SignInViewModel,
    navController: NavController
) {

    when (val signInResponse = viewModel.emailAndPasswordSignInResponse) {
        is ResponseEmailPassword.Loading -> {
            viewModel.showLoadingState.value = true
        }

        is ResponseEmailPassword.Success -> {
            viewModel.showLoadingState.value = false
            val isUserSignedIn = signInResponse.data
            LaunchedEffect(key1 = isUserSignedIn) {
                viewModel.reloadUser()
                if (isUserSignedIn && !viewModel.signInWithGoogle.value) {
                    //Log.d("isEmailVerified", "sign in View: ${viewModel.getEmailVerfiedState()}")
                    if (viewModel.getEmailVerfiedState()) {
                        //Log.d("isUserVerified", "navigated from sign in view home")
                        navController.navigate("home_Screen")
                    } else {
                        //Log.d("isUserVerified", "navigated from sign in view verify")
                        navController.navigate("verifiy_email_Screen")
                    }
                }
            }
        }

        is ResponseEmailPassword.Failure -> signInResponse.apply {
            viewModel.showLoadingState.value = false
            //Log.d("checkSignIN", authErrors(e.message))
            viewModel.errorMessage.value = authErrors(e.message)

        }
    }

}