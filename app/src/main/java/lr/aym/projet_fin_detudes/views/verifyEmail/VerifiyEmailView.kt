package lr.aym.projet_fin_detudes.views.verifyEmail

import android.util.Log
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.LoadingTextButton

@Composable
fun VerifiyEmailView(
    viewModel: VerifiyEmailViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Verify your email", color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "An Email has been sent to your email address." +
                        " please click the link in the email to verify your email address",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = viewModel.errorMessage.value,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoadingTextButton(
                    showLoadingState = viewModel.showLoadingStateContinue,
                    text = "Continue",
                    width = 150.dp
                ) {
                    viewModel.isEmailVerified()
                    viewModel.showErrorMessage = true
                }
                Text(
                    text = "Resend?",
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
                if (viewModel.verifiedEmail) {
                    navController.navigate("additional_info_signUp")
                }
            }
            if (viewModel.showErrorMessage && !viewModel.verifiedEmail) {
                Log.d("verify error", "Verifiy email first")
                viewModel.errorMessage.value = "error: please verify you email first."
            }

        }


    }

}

