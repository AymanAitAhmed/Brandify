package lr.aym.projet_fin_detudes.views.sign_in_up_process.verifyEmail


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FacebookAuthProvider
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.LoadingTextButton
import lr.aym.projet_fin_detudes.components.Screens

@Composable
fun VerifiyEmailView(
    viewModel: VerifiyEmailViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(key1 = viewModel.currentUser?.providerId) {
        val userProvider = viewModel.currentUser?.providerData
        if (userProvider != null) {
            for (profile in userProvider) {
                if (profile.providerId == FacebookAuthProvider.PROVIDER_ID) {
                    viewModel.facebookProvider.value = true
                    viewModel.resendVerificationEmail()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VerifyEmailTopBar(onBackClick = {
                viewModel.signOut()
                navController.popBackStack()
            })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Image(
                    painter = painterResource(id = R.drawable.email_sent),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(shape = CircleShape)

                )
                Text(
                    text = stringResource(id = R.string.Verify_email),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = stringResource(
                        id = R.string.Verify_email_sent,
                        "${viewModel.currentUser?.email}"
                    ),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
                if (viewModel.showErrorMessage){
                    Text(
                        text = viewModel.errorMessage.value.asString(),
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LoadingTextButton(
                        showLoadingState = viewModel.showLoadingStateContinue,
                        text = stringResource(id = R.string.Continue),
                        width = 150.dp
                    ) {
                        viewModel.isEmailVerified()
                    }
                    Text(
                        text = stringResource(id = R.string.Resend),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                viewModel.resendVerificationEmail()
                            },
                        color = MaterialTheme.colors.onSecondary
                    )
                }

                LaunchedEffect(key1 = viewModel.verifiedEmail) {
                    if (viewModel.verifiedEmail && viewModel.facebookProvider.value) {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(route = Screens.VerifyEmailScreen.route) {
                                inclusive = true
                            }
                        }
                    } else if (viewModel.verifiedEmail) {
                        navController.navigate(Screens.AdditionalInfoScreen.route) {
                            popUpTo(route = Screens.VerifyEmailScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}

