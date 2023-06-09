package lr.aym.projet_fin_detudes.views.sign_in_up_process.reset_Password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ResetPasswordTopBar(onBackClick = { navController.popBackStack() }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f),

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
                    text = stringResource(id = R.string.Reset_password),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                Text(
                    text = stringResource(id = R.string.Reset_password_message),
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
                        text = stringResource(id = R.string.Email_message),
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
                        Text(
                            text = stringResource(id = R.string.Email),
                            color = MaterialTheme.colors.onSecondary
                        )
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
                    }, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    LoadingTextButton(
                        showLoadingState = viewModel.showLoadingState,
                        text = stringResource(id = R.string.Send),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.primary)
                            .clickable { viewModel.sendResetPasswordEmail() }
                    )
                }
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

