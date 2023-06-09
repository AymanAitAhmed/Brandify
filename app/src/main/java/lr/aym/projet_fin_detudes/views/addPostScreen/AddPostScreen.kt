package lr.aym.projet_fin_detudes.views.addPostScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.Constants
import lr.aym.projet_fin_detudes.components.SignedOutDialog
import lr.aym.projet_fin_detudes.components.UiText

@Composable
fun AddPostScreen(
    viewModel: AddPostViewModel = hiltViewModel(),
    navController: NavController
) {

    val scaffoldState = rememberScaffoldState()

    val multipleImagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri: List<@JvmSuppressWildcards Uri> ->
            viewModel.postImages.value = uri
            viewModel.isButtonActivated()
        }

    val onDoneDialogState = viewModel.onDoneDialogState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel.logUserOut.value ){
        if (viewModel.logUserOut.value){
            val signOutResponse = viewModel.signOut()
            if (signOutResponse){
                viewModel.showSignedOutDialog.value = true
            }
        }
    }

    if (viewModel.showSignedOutDialog.value){
        SignedOutDialog(navController = navController, showEmailSentDialog = viewModel.showSignedOutDialog)
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AddPostTopBar(
                onBackPressButton = {
                    navController.popBackStack()
                },
                onDonePressButton = {
                    viewModel.sendNewPostForReview()
                },
                doneButtonActivated = viewModel.doneButtonActivated.value
            )
        },
    ) { contentPadding ->

        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {

            OnDoneDialog(onDoneDialogState = onDoneDialogState, viewModel = viewModel)

            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${stringResource(id = R.string.Description)} :",
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }


                TextField(
                    value = viewModel.descriptionTextField.value,
                    onValueChange = {
                        if (it.length<Constants.ADD_POST_DESCRIPTION_MAX_LENGTH){
                            viewModel.descriptionTextField.value = it
                            viewModel.isButtonActivated()
                        }
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.Description_Placeholder))
                    },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f)
                        .verticalScroll(rememberScrollState()),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                Row(
                    Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "${viewModel.descriptionTextField.value.length}/${Constants.ADD_POST_DESCRIPTION_MAX_LENGTH}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1
                    )
                }
                if (viewModel.descriptionError.value.asString() != UiText.StringResource(R.string.empty_string)
                        .asString()
                ) {
                    Text(
                        text = viewModel.descriptionError.value.asString(),
                        color = Color.Red,
                        fontSize = 10.sp
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(1.dp)
                )
                Text(
                    text = stringResource(id = R.string.pictures),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                LazyHorizontalGrid(rows = GridCells.Fixed(1), modifier = Modifier.height(150.dp)) {

                    items(viewModel.postImages.value) { postImage ->
                        Box(contentAlignment = Alignment.Center) {
                            AsyncImage(
                                model = postImage,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxHeight()
                                    .width(100.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop,
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_do_disturb_on_24),
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier
                                    .offset(x = 45.dp, y = (-65).dp)
                                    .size(20.dp)
                                    .shadow(5.dp)
                                    .clickable {
                                        viewModel.postImages.value =
                                            viewModel.postImages.value.filter {
                                                it != postImage
                                            }
                                        viewModel.isButtonActivated()
                                    }

                            )

                        }

                    }
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSystemInDarkTheme()) Color(0x52A8A8A8) else Color(
                                        0x32494949
                                    )
                                )
                                .clickable {
                                    multipleImagePicker.launch("image/*")
                                }) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null,
                                    tint = if (isSystemInDarkTheme()) Color(0xD9A8A8A8) else Color(
                                        0xE5494949
                                    ),
                                    modifier = Modifier.size(50.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.add_image),
                                    color = MaterialTheme.colors.onBackground,
                                    fontSize = 13.sp
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AddPostTopBar(
    onBackPressButton: () -> Unit,
    onDonePressButton: () -> Unit,
    doneButtonActivated: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackPressButton
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        }

        Text(
            text = stringResource(id = R.string.Add_Post),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h6
        )

        Button(
            onClick = onDonePressButton,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onBackground,
                disabledBackgroundColor = MaterialTheme.colors.primary
            ),
            enabled = doneButtonActivated
        ) {
            Text(text = stringResource(id = R.string.Done))
        }
    }

}