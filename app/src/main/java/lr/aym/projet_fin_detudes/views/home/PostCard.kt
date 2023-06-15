package lr.aym.projet_fin_detudes.views.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.PicsLayout
import lr.aym.projet_fin_detudes.model.posting.PostDetailsFromFb

@Composable
fun PostCard(
    postDetailsFromFb: PostDetailsFromFb,
    username: String,
    onDeleteClick: () -> Unit,
    onDeleteLoadingState: MutableState<Boolean>
) {

    val showMenu = remember {
        mutableStateOf(false)
    }
    val showPostOptions = remember {
        mutableStateOf(false)
    }
    val showFullText = remember {
        mutableStateOf(false)
    }
    val showDeletePostDialog = remember {
        mutableStateOf(false)
    }


    Card(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onBackground
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (showDeletePostDialog.value) {
                DeletePostConfirmationDialog(onYesClicked = {
                    onDeleteClick()
                    showDeletePostDialog.value = false
                },
                    onDismiss = {
                        showDeletePostDialog.value = false
                    }
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .border(
                                    2.dp, MaterialTheme.colors.onBackground, CircleShape
                                ),

                            )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = username,
                            style = MaterialTheme.typography.body1
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(!onDeleteLoadingState.value){
                            DropdownMenu(
                                expanded = showPostOptions.value,
                                onDismissRequest = {
                                    showPostOptions.value = false
                                },
                                modifier = Modifier.background(MaterialTheme.colors.background)
                            ) {

                                DropdownMenuItem(
                                    onClick = {
                                        showPostOptions.value = false
                                        showDeletePostDialog.value = true
                                    }
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.delete),
                                        color = MaterialTheme.colors.onBackground
                                    )
                                }
                            }
                            IconButton(onClick = {
                                showPostOptions.value = true
                            }) {
                                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                            }
                        }else{
                            CircularProgressIndicator(color = MaterialTheme.colors.primary)
                        }

                    }


                }
                Spacer(modifier = Modifier.size(8.dp))
                if (!showFullText.value) {
                    Text(
                        text = postDetailsFromFb.message,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .clickable {
                                showFullText.value = !showFullText.value
                            }
                    )
                } else {
                    Text(
                        text = postDetailsFromFb.message,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showFullText.value = !showFullText.value
                            }
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))
                postDetailsFromFb.images?.let {
                    PicsLayout(imagesUrls = it)
                    Spacer(modifier = Modifier.size(8.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            showMenu.value = true
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_thumb_up_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "${postDetailsFromFb.like + postDetailsFromFb.love + postDetailsFromFb.haha + postDetailsFromFb.wow + postDetailsFromFb.angry + postDetailsFromFb.sad}",
                            style = MaterialTheme.typography.body1
                        )
                        DropdownMenu(
                            expanded = showMenu.value,
                            onDismissRequest = {
                                showMenu.value = false
                            }) {
                            DropdownMenuItem(onClick = { /*TODO*/ }, enabled = false) {
                                Reaction(
                                    id = R.drawable.baseline_thumb_up_24,
                                    number = postDetailsFromFb.like,
                                    onClick = {})
                            }
                            DropdownMenuItem(onClick = { /*TODO*/ }, enabled = false) {
                                ReactionImage(
                                    id = R.drawable.facebook_love,
                                    number = postDetailsFromFb.love
                                )
                            }
                            DropdownMenuItem(onClick = { /*TODO*/ }, enabled = false) {
                                ReactionImage(
                                    id = R.drawable.facebook_haha_logo,
                                    number = postDetailsFromFb.haha
                                )
                            }
                            DropdownMenuItem(onClick = { /*TODO*/ }, enabled = false) {
                                ReactionImage(
                                    id = R.drawable.facebook_wow_logo,
                                    number = postDetailsFromFb.wow
                                )
                            }
                            DropdownMenuItem(onClick = { /*TODO*/ }, enabled = false) {
                                ReactionImage(
                                    id = R.drawable.facebook_angry,
                                    number = postDetailsFromFb.angry
                                )
                            }
                            DropdownMenuItem(onClick = { /*TODO*/ }, enabled = false) {
                                ReactionImage(
                                    id = R.drawable.facebook_sad_logo,
                                    number = postDetailsFromFb.sad
                                )
                            }


                        }
                    }


                    Reaction(
                        id = R.drawable.baseline_comment_24,
                        number = postDetailsFromFb.comments ?: 0,
                        onClick = {})
                    Reaction(
                        id = R.drawable.baseline_share_24,
                        number = postDetailsFromFb.shares ?: 0,
                        onClick = {})
                }


            }
        }
    }


}

@Composable
fun Reaction(id: Int, number: Int, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = id), contentDescription = null, modifier = Modifier
                .size(25.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "$number ", style = MaterialTheme.typography.body1)
    }
}

@Composable
fun ReactionImage(id: Int, number: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "$number ", style = MaterialTheme.typography.body1)
    }

}