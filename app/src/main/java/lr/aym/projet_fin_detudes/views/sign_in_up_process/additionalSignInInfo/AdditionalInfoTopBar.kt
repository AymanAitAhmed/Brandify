package lr.aym.projet_fin_detudes.views.sign_in_up_process.additionalSignInInfo

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
fun AdditionalInfoTopBar(
    onBackClick: () -> Unit
) {

    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        title = {
            Text(
                text = stringResource(id = R.string.additional_info),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }

    )

}