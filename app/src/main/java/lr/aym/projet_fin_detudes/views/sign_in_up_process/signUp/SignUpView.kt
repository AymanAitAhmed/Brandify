package lr.aym.projet_fin_detudes.views.sign_in_up_process.signUp

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.components.authErrors
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword

@Composable
fun SignUpView(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
    navigateToSignInScreen: () -> Unit,
) {

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
                text = stringResource(id = R.string.Sign_Up),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(8.dp)

            )

            Text(
                text = stringResource(id = R.string.Email),
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
                    Text(stringResource(id = R.string.Email))
                }
            )

            if (viewModel.emailErrorMessage.value.asString() != UiText.StringResource(R.string.empty_string).asString()){
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth(0.7f)) {
                    Text(
                        text = viewModel.emailErrorMessage.value.asString(),
                        color = Color.Red,
                        fontSize = 10.sp
                    )
                }
            }


            Text(
                text = stringResource(id = R.string.Password),
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

            if (viewModel.passwordErrorMessage.value.asString() != UiText.StringResource(R.string.empty_string).asString()){
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth(0.7f)) {
                    Text(
                        text = viewModel.passwordErrorMessage.value.asString(),
                        color = Color.Red,
                        fontSize = 10.sp
                    )
                }
            }


            Text(
                text = stringResource(id = R.string.Confirm_Password),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 90.dp, top = 12.dp, bottom = 8.dp)
            )
            OutlinedTextField(
                value = viewModel.confirmPasswordTextFieldValue.value,
                onValueChange = { viewModel.confirmPasswordTextFieldValue.value = it },
                shape = RoundedCornerShape(30.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,

                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.Confirm_Password))
                },
                visualTransformation = if (viewModel.showHidePassword.value) VisualTransformation.None
                else PasswordVisualTransformation()

            )

            if (viewModel.confirmErrorMessage.value.asString() != UiText.StringResource(R.string.empty_string).asString()){
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth(0.7f)) {
                    Text(
                        text = viewModel.confirmErrorMessage.value.asString(),
                        color = Color.Red,
                        fontSize = 10.sp
                    )
                }
            }


            if (viewModel.errorMessage.value != ""){
                Text(text = viewModel.errorMessage.value, color = Color.Red, fontSize = 10.sp)
            }

            LoadingTextButton(
                showLoadingState = viewModel.showLoadingState,
                text = stringResource(id = R.string.Sign_Up),
                padding = 16.dp
            ) {
                viewModel.errorMessage.value = ""
                viewModel.passwordChecks()
                viewModel.emailChecks()
                if (viewModel.passwordChecks() && viewModel.emailChecks()){
                    viewModel.onSignUpWithEmailAndPassword()
                    //Log.d("signuptag", "SignUp button clicked")
                }

            }


            Text(
                text = stringResource(id = R.string.Already_have_account_message),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable(onClick = navigateToSignInScreen)
            )

        }
    }

    when (val signUpResponse = viewModel.signUpResponseEmailPassword) {
        is ResponseEmailPassword.Loading -> {
            viewModel.showLoadingState.value = true
        }

        is ResponseEmailPassword.Failure -> signUpResponse.apply {
            LaunchedEffect(key1 = e) {
                viewModel.showLoadingState.value = false
                viewModel.errorMessage.value = authErrors(e.message)
                Log.d("signuptag", authErrors(e.message))
            }
        }

        is ResponseEmailPassword.Success -> {
            viewModel.showLoadingState.value = false
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(key1 = isUserSignedUp) {
                if (isUserSignedUp) {
                    viewModel.sendEmailVerification()
                    //Log.d("signuptag", "SignUp verification triggered")
                    navController.navigate(Screens.VerifyEmailScreen.route){
                        popUpTo(route = Screens.SignUpScreen.route){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

}