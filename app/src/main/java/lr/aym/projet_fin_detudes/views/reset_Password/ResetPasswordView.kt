package lr.aym.projet_fin_detudes.views.reset_Password

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.LoadingTextButton
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword


@Composable
fun ResetPasswordView(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    navController: NavController
) {

    if (viewModel.showEmailSentDialog.value) {
        EmailSentDialog(
            navController = navController,
            showEmailSentDialog = viewModel.showEmailSentDialog
        )
    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.reset_email),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(top = 90.dp)
                    .size(200.dp),
            )
            Text(
                text = "Reset Password",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            Text(
                text = "Enter the email associated with your account and we'll send an email with instructions to reset your password.",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Email address : ",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(top = 30.dp)
                )
            }

            OutlinedTextField(
                value = viewModel.emailValue.value,
                onValueChange = {
                    viewModel.emailValue.value = it
                },
                shape = RoundedCornerShape(15.dp), label = {
                    Text(text = "Email ", color = MaterialTheme.colors.onSecondary)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background
                ), leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }, modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                LoadingTextButton(
                    showLoadingState = viewModel.showLoadingState,
                    text = "Send",
                    width = 110.dp
                ) {
                    viewModel.sendResetPasswordEmail()
                }
                Text(
                    text = "return to Login?",
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            navController.navigate("signIn_Screen")
                        },
                    color = MaterialTheme.colors.onSecondary
                )

            }
        }
    }

    when (val resetPasswordResoponse = viewModel.resetPasswordResponse) {
        is ResponseEmailPassword.Loading -> {
            viewModel.showLoadingState.value = true
        }

        is ResponseEmailPassword.Failure -> {
            resetPasswordResoponse.apply {
                viewModel.showLoadingState.value = false
                viewModel.errorMessage.value = "${e.message}"
            }
        }

        is ResponseEmailPassword.Success -> {
            val isResetEmailPasswordSent = resetPasswordResoponse.data
            LaunchedEffect(key1 = isResetEmailPasswordSent) {
                if (isResetEmailPasswordSent) {
                    viewModel.showEmailSentDialog.value = true
                }
            }
        }
    }
}

