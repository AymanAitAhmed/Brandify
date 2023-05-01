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
import lr.aym.projet_fin_detudes.ui.theme.Projet_fin_detudesTheme
import lr.aym.projet_fin_detudes.views.additionalSignInInfo.AdditionalSignInInfo
import lr.aym.projet_fin_detudes.views.home.Home
import lr.aym.projet_fin_detudes.views.reset_Password.ResetPasswordView
import lr.aym.projet_fin_detudes.views.signIn.SignInView
import lr.aym.projet_fin_detudes.views.signUp.SignUpView
import lr.aym.projet_fin_detudes.views.splashScreen.SplashScreen
import lr.aym.projet_fin_detudes.views.verifyEmail.VerifiyEmailView
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
        val viewModel by viewModels<MainViewModel>()
        setContent {
            val navController = rememberNavController()
            Projet_fin_detudesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val isUserSignedOut = viewModel.getAuthState().collectAsStateWithLifecycle().value
                    val startDestination = if (isUserSignedOut){
                            "signIn_Screen"
                        }else{
                            if (viewModel.isEmailVerified){
                                if (viewModel.isUserInfoExist()){
                                    Log.d("navigatedFrom", "MainActivity")
                                    "home_Screen"
                                }else{
                                    "additional_info_signUp"
                                }

                            }else{
                                "verifiy_email_Screen"
                            }
                        }

                    //Log.d("isUserVerified", "onCreate: $startDestination")

                    NavHost(navController = navController, startDestination = "splash_Screen") {

                        composable("splash_Screen"){
                            SplashScreen (navController, startDestination)
                        }

                        composable(route = "signIn_Screen") {
                            SignInView(navigateToSignUpScreen = {
                                navController.navigate("signUp_Screen")
                            }, navController = navController)
                        }
                        composable(route = "reset_password_Screen"){
                            ResetPasswordView(navController = navController)
                        }
                        composable(route = "signUp_Screen") {
                            SignUpView(
                                navigateToSignInScreen = {
                                    navController.navigate("signIn_Screen")
                                }, navController = navController
                            )
                        }
                        composable(route = "additional_info_signUp"){
                            AdditionalSignInInfo(navController = navController)
                        }

                        composable("verifiy_email_Screen") {
                            VerifiyEmailView(navController = navController)
                        }
                        composable("home_Screen") {
                            Home(navController = navController)
                        }


                    }



                }
            }
        }

    }
}

