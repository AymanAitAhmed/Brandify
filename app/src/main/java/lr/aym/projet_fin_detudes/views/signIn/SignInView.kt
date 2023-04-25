package lr.aym.projet_fin_detudes.views.signIn

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.LoadingTextButton
import lr.aym.projet_fin_detudes.components.authErrors
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle

@Composable
fun SignInView(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController,
    navigateToSignUpScreen: () -> Unit
) {
    var signInWithGoogle = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)

            )
            Text(
                text = stringResource(id = R.string.Sign_In),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(8.dp)

            )

            Text(
                text = "${stringResource(id = R.string.Email)} :",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 90.dp, top = 12.dp)
            )

            OutlinedTextField(value = viewModel.emailTextFieldValue.value,
                onValueChange = { viewModel.emailTextFieldValue.value = it },
                shape = RoundedCornerShape(30.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.Email))
                }
            )

            Text(
                text = "${stringResource(id = R.string.Password)} :",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 90.dp, top = 12.dp)
            )
            OutlinedTextField(
                value = viewModel.passwordTextFieldValue.value,
                onValueChange = { viewModel.passwordTextFieldValue.value = it },
                shape = RoundedCornerShape(30.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,

                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                        contentDescription = null,
                        tint = if (viewModel.showHidePassword.value) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                viewModel.onShowHideEyeClick()
                            }
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.Password))
                },
                visualTransformation = if (viewModel.showHidePassword.value) VisualTransformation.None
                else PasswordVisualTransformation()

            )
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(
                    text = stringResource(id = R.string.Forgot_password),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp)
                        .clickable {
                            navController.navigate("reset_password_Screen")
                        }
                )
            }


            Text(text = viewModel.errorMessage.value, color = Color.Red, fontSize = 10.sp)


            LoadingTextButton(
                showLoadingState = viewModel.showLoadingState,
                text = stringResource(id = R.string.Sign_In),
                padding = 16.dp
            ) {
                signInWithGoogle.value = false
                viewModel.onSignInWithEmailAndPasswordClick()
            }

            Text(
                text = "${stringResource(id = R.string.or_login_with)}:",
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook_logo),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(45.dp)
                    )
                }
                IconButton(onClick = {
                    signInWithGoogle.value = true
                    viewModel.onSignInWithGoogleClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(45.dp)
                    )
                }

            }
            Text(
                text = stringResource(id = R.string.no_Account),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable(onClick = navigateToSignUpScreen)
            )
        }

    }

    when (val signInResponse = viewModel.emailAndPasswordSignInResponse) {
        is ResponseEmailPassword.Loading -> {
            viewModel.showLoadingState.value = true
        }

        is ResponseEmailPassword.Success -> {
            viewModel.showLoadingState.value = false
            val isUserSignedIn = signInResponse.data
            LaunchedEffect(key1 = isUserSignedIn) {
                viewModel.reloadUser()
                if (isUserSignedIn && !signInWithGoogle.value) {
                    Log.d("isEmailVerified", "sign in View: ${viewModel.getEmailVerfiedState()}")
                    if (viewModel.getEmailVerfiedState()) {
                        Log.d("isUserVerified", "navigated from sign in view home")
                        navController.navigate("home_Screen")
                    } else {
                        Log.d("isUserVerified", "navigated from sign in view verify")
                        navController.navigate("verifiy_email_Screen")
                    }
                }
            }
        }

        is ResponseEmailPassword.Failure -> signInResponse.apply {
            viewModel.showLoadingState.value = false
            Log.d("checkSignIN", authErrors(e.message))
            viewModel.errorMessage.value = authErrors(e.message)

        }
    }


    //Google authentication
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credentials =
                        viewModel.onTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = getCredential(googleIdToken, null)
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
            Log.d("checkSignIN", "Loading:Google OneTapResponse ")
        }

        is ResponseGoogle.Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                Log.d("checkSignIN", "Success:Google OneTapResponse ")
                launch(it)
            }
        }

        is ResponseGoogle.Failure -> LaunchedEffect(Unit) {
            Log.d("checkSignIN", "Failure:Google OneTapResponse ${oneTapSignInResponse.e.message}")
        }
    }

    when (val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is ResponseGoogle.Loading -> {
            Log.d("checkSignIN", "Loading:signInGoogleResponse ")
        }

        is ResponseGoogle.Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                if (signedIn) {
                    Log.d("checkSignIN", "Success:signInGoogleResponse ")
                    Log.d("navigatedFrom", "Sign In google")
                    navController.navigate("home_Screen")
                }
            }
        }

        is ResponseGoogle.Failure -> LaunchedEffect(Unit) {
            Log.d(
                "checkSignIN",
                "Failure:signInGoogleResponse ${signInWithGoogleResponse.e.message} "
            )
        }
    }

}
