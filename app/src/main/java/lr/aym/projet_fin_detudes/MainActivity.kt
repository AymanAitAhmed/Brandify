package lr.aym.projet_fin_detudes

import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import lr.aym.projet_fin_detudes.components.Screens
import lr.aym.projet_fin_detudes.ui.theme.Projet_fin_detudesTheme
import lr.aym.projet_fin_detudes.views.addPostScreen.AddPostScreen
import lr.aym.projet_fin_detudes.views.addPostScreen.AddPostViewModel
import lr.aym.projet_fin_detudes.views.home.Home
import lr.aym.projet_fin_detudes.views.sign_in_up_process.additionalSignInInfo.AdditionalSignInInfo
import lr.aym.projet_fin_detudes.views.sign_in_up_process.reset_Password.ResetPasswordView
import lr.aym.projet_fin_detudes.views.sign_in_up_process.signIn.SignInView
import lr.aym.projet_fin_detudes.views.sign_in_up_process.signUp.SignUpView
import lr.aym.projet_fin_detudes.views.splashScreen.SplashScreen
import lr.aym.projet_fin_detudes.views.sign_in_up_process.verifyEmail.VerifiyEmailView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        try {
            val info = packageManager.getPackageInfo(
                "lr.aym.projet_fin_detudes",  //Insert your own package name.
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }

        super.onCreate(savedInstanceState)
        val mainViewModel by viewModels<MainViewModel>()
        val addPostViewModel by viewModels<AddPostViewModel>()
        setContent {
            val navController = rememberNavController()
            Projet_fin_detudesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val isUserSignedOut = mainViewModel.getAuthState().collectAsStateWithLifecycle().value

                    mainViewModel.startDestination.value = if (isUserSignedOut){
                            Screens.SignInScreen.route
                        }else{
                            if (mainViewModel.isEmailVerified){
                                if (mainViewModel.doesUserExist.value){
                                    Log.d("navigatedFrom", "MainActivity")
                                    Screens.HomeScreen.route
                                }else{
                                    Log.d("userExist", "does user existe: ${mainViewModel.doesUserExist.value}")
                                    Screens.AdditionalInfoScreen.route
                                }

                            }else{
                                Screens.VerifyEmailScreen.route
                            }
                        }

                    //Log.d("isUserVerified", "onCreate: $startDestination")

                    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {

                        composable(Screens.SplashScreen.route){
                            SplashScreen (navController, mainViewModel)
                        }

                        composable(route = Screens.SignInScreen.route) {
                            SignInView(navigateToSignUpScreen = {
                                navController.navigate(Screens.SignUpScreen.route)
                            }, navController = navController)
                        }
                        composable(route = Screens.ResetPasswordScreen.route){
                            ResetPasswordView(navController = navController)
                        }
                        composable(route = Screens.SignUpScreen.route) {
                            SignUpView(
                                navigateToSignInScreen = {
                                    navController.navigate(Screens.SignInScreen.route)
                                }, navController = navController
                            )
                        }
                        composable(route = Screens.AdditionalInfoScreen.route){
                            AdditionalSignInInfo(navController = navController)
                        }

                        composable(route = Screens.VerifyEmailScreen.route) {
                            VerifiyEmailView(navController = navController)
                        }
                        composable(route = Screens.HomeScreen.route) {
                            Home(navController = navController)
                        }
                        composable(route = Screens.AddPostScreen.route){
                            AddPostScreen(addPostViewModel,navController)
                        }


                    }



                }
            }
        }

    }
}

