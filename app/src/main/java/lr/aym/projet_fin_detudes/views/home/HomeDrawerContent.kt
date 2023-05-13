package lr.aym.projet_fin_detudes.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.User

@Composable
fun HomeDrawerContent(
    viewModel: HomeViewModel,
    navController: NavController,
    currentUser: User
) {

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            currentUser.profilePicture?.let { profilePic ->
                AsyncImage(
                    model = profilePic,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .size(150.dp)
                )
            } ?: Icon(
                imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .size(150.dp)
                    .padding(8.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
        item {
            Text(
                text = "${currentUser.username}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )
        }
        item {
            DrawerItemRow(
                trailingIcon = R.drawable.baseline_data_usage_24,
                rowName = R.string.statistics
            ) {

            }
        }
        item {
            LinkWithFacebookButton(
                onLinkSuccesfful = {
                    viewModel.messageToShowAfterLinking.value =
                        UiText.StringResource(id = R.string.account_linked_succesfully)
                },
                onLinkError = {
                    viewModel.messageToShowAfterLinking.value =
                        UiText.StringResource(id = R.string.error_while_linking, "${it.message}")
                }
            )
        }
        item {
            DrawerItemRow(
                trailingIcon = R.drawable.baseline_settings_24,
                rowName = R.string.settings
            ) {

            }
        }

        item {
            DrawerItemRow(
                trailingIcon = R.drawable.baseline_login_24,
                rowName = R.string.Sign_Out
            ) {
                viewModel.signOut()
            }
        }

    }

}

@Composable
fun DrawerItemRow(trailingIcon: Int, rowName: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 4.dp, end = 4.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = trailingIcon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .rotate(180f)
                .size(30.dp),
            tint = MaterialTheme.colors.primary
        )
        Text(
            text = stringResource(id = rowName),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

    }
    Divider(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(end = 16.dp),
        color = MaterialTheme.colors.onSecondary
    )
}