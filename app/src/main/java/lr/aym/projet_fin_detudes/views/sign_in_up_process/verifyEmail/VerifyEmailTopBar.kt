package lr.aym.projet_fin_detudes.views.sign_in_up_process.verifyEmail

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import lr.aym.projet_fin_detudes.R

@Composable
fun VerifyEmailTopBar(
    onBackClick: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.verify_email),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )

}