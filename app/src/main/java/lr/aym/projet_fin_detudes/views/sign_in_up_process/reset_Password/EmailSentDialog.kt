package lr.aym.projet_fin_detudes.views.sign_in_up_process.reset_Password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.Screens

@Composable
fun EmailSentDialog(
    navController: NavController,
    showEmailSentDialog: MutableState<Boolean>
) {

    AlertDialog(modifier = Modifier
        .width(380.dp)
        .height(280.dp),
        backgroundColor = MaterialTheme.colors.surface,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.email_sent),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(80.dp),
                )

            }
        }, text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.Email_sent),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = R.string.Email_Reset_password_dialog_message),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground
                )

            }
        }, onDismissRequest = {

        }, confirmButton = {
            Button(
                onClick = {
                    showEmailSentDialog.value = false
                    navController.navigate(Screens.SignInScreen.route){
                        popUpTo(route = Screens.ResetPasswordScreen.route){
                            inclusive = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onSecondary
                )
            ) {

                Text(text = stringResource(id = R.string.Return_SignIn),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 8.dp))
            }
        })
}


