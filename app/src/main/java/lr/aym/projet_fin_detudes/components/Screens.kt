package lr.aym.projet_fin_detudes.components

sealed class Screens(val route:String){
    object SplashScreen : Screens("splash_Screen")
    object SignInScreen : Screens("signIn_Screen")
    object SignUpScreen : Screens("signUp_Screen")
    object ResetPasswordScreen : Screens("reset_password_Screen")
    object VerifyEmailScreen : Screens("verifiy_email_Screen")
    object AdditionalInfoScreen : Screens("additional_info_signUp")
    object HomeScreen : Screens("home_Screen")
    object AddPostScreen : Screens("add_post_Screen")
}
