package lr.aym.projet_fin_detudes.views.sign_in_up_process.signIn

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.components.Screens
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
            val isUserSignedIn = signInResponse.data
            LaunchedEffect(key1 = isUserSignedIn) {
                viewModel.reloadUser()
                if (isUserSignedIn && !viewModel.signInWithGoogle.value) {
                    //Log.d("isEmailVerified", "sign in View: ${viewModel.getEmailVerfiedState()}")
                    if (viewModel.getEmailVerfiedState()) {
                        Log.d("userExist", "sign in does user exist: ${viewModel.doesUserExist()}")
                        if (viewModel.doesUserExist()){
                            viewModel.showLoadingState.value = false
                            //Log.d("isUserVerified", "navigated from sign in view home")
                        navController.navigate(Screens.HomeScreen.route, builder = {
                            popUpTo(Screens.SignInScreen.route) {
                                inclusive = true
                            }
                        })
                        }
                        else{
                            viewModel.showLoadingState.value = false
                            navController.navigate(Screens.AdditionalInfoScreen.route, builder = {
                                popUpTo(Screens.SignInScreen.route) {
                                    inclusive = true
                                }
                            })
                        }
                    } else {
                        viewModel.showLoadingState.value = false
                        //Log.d("isUserVerified", "navigated from sign in view verify")
                        navController.navigate(Screens.VerifyEmailScreen.route, builder = {
                            popUpTo(Screens.SignInScreen.route) {
                                inclusive = true
                            }
                        })
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