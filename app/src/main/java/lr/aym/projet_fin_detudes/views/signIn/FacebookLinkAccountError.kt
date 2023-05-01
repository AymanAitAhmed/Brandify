package lr.aym.projet_fin_detudes.views.signIn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import lr.aym.projet_fin_detudes.R

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
                    text = stringResource(id = R.string.oops),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primaryVariant
                )

                Text(
                    text = stringResource(id = R.string.link_facebook_account_message),
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