package lr.aym.projet_fin_detudes.views.sign_in_up_process.signIn


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
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.LoadingTextButton
import lr.aym.projet_fin_detudes.components.Screens

@Composable
fun SignInView(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController,
    navigateToSignUpScreen: () -> Unit
) {


    if (viewModel.showFacebookLinkAccountDialog.value) {
        FacebookLinkAccountError(showDialog = viewModel.showFacebookLinkAccountDialog)
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
                            navController.navigate(Screens.ResetPasswordScreen.route)
                        }
                )
            }


            Text(text = viewModel.errorMessage.value, color = Color.Red, fontSize = 10.sp)


            LoadingTextButton(
                showLoadingState = viewModel.showLoadingState,
                text = stringResource(id = R.string.Sign_In),
                padding = 16.dp
            ) {
                viewModel.signInWithGoogle.value = false
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

                FacebookLoginButton(
                    onAuthComplete = { navController.navigate(Screens.HomeScreen.route) },
                    onAuthError = {
                        // Log.d("fbsignin", "${it.message}")
                        if (it.message == "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.") {
                            //Log.d("fbsignin", "executed")
                            viewModel.showFacebookLinkAccountDialog.value = true
                        }

                    })

                IconButton(onClick = {
                    viewModel.signInWithGoogle.value = true
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

    EmailPasswordSignIn(viewModel = viewModel, navController = navController)


    //Google authentication

    GoogleSignIn(viewModel = viewModel, navController = navController)

}

