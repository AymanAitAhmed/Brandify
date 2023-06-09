package lr.aym.projet_fin_detudes.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lr.aym.projet_fin_detudes.R

@Composable
fun HomeTopBar(
    onMenuClick : ()->Unit,
    onSortListClick : () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {

        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(35.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.all_posts),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground
        )

        IconButton(onClick = onSortListClick) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(35.dp)
            )
        }

    }
}