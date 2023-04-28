package lr.aym.projet_fin_detudes.views.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FacebookLinkAccountError(
    showDialog: MutableState<Boolean>
) {

    AlertDialog(
        backgroundColor = MaterialTheme.colors.surface,
        title = {
        }, text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Oops..!",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primaryVariant
                )

                Text(
                    text = "The email used in your facebook account has already an exsisting account. Please login to your original account first and then link it with your facebook.",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onBackground
                )

            }
        }, onDismissRequest = {
            showDialog.value = false
        }, confirmButton = {

        })

}