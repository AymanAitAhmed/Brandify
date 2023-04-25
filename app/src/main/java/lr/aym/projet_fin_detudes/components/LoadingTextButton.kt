package lr.aym.projet_fin_detudes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun LoadingTextButton(
    showLoadingState: MutableState<Boolean>,
    text: String,
    width: Dp = 130.dp,
    height: Dp = 50.dp,
    padding:Dp = 0.dp,
    roundedCornerShapePercent: Int = 30,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(padding)
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(roundedCornerShapePercent))
            .background(MaterialTheme.colors.primary)
            .clickable(onClick = onClick)

    ) {
        if (!showLoadingState.value) {
            Text(
                text = text,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 8.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .height(35.dp)
                    .width(45.dp)
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.padding(start = 40.dp),
                MaterialTheme.colors.onBackground
            )
        }
    }
}