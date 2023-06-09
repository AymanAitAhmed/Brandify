package lr.aym.projet_fin_detudes.views.postsInReview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import lr.aym.projet_fin_detudes.R

@Composable
fun PostCardInReview(
    userUID: String?,
    message: String?,
    imagesUrls: List<String>?
) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            imagesUrls?.let {
                SubcomposeAsyncImage(
                    model = it.first(), contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(70.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        ), loading = {
                        CircularProgressIndicator()
                    }, error = {
                        painterResource(id = R.drawable.baseline_cancel_24)
                    },
                    contentScale = ContentScale.Crop
                )
            }




            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "$userUID",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "$message",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.body2,
                    color = if (isSystemInDarkTheme()) Color(0xFF959595) else Color(0xFFBABABA)
                )
            }

        }
    }


}

