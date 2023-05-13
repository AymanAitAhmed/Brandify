package lr.aym.projet_fin_detudes.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.Screens
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val scafolldState = rememberScaffoldState()

    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    val linkingMessage = viewModel.messageToShowAfterLinking.collectAsStateWithLifecycle().value.asString()
    Scaffold(scaffoldState = scafolldState,
        topBar = {

        },
        drawerContent = {
            HomeDrawerContent(
                viewModel = viewModel,
                navController = navController,
                currentUser = currentUser
            )
        },
        drawerScrimColor = MaterialTheme.colors.background,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.AddPostScreen.route)
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background
                )
            }
        }) { paddingValues ->

        LaunchedEffect(key1 = linkingMessage){
            if (linkingMessage!="" && linkingMessage != "empty"){
                val snackBarResult = scafolldState.snackbarHostState.showSnackbar(message = linkingMessage)
                when(snackBarResult){
                    SnackbarResult.Dismissed -> viewModel.messageToShowAfterLinking.value  = UiText.StringResource(R.string.empty_string)
                    SnackbarResult.ActionPerformed -> viewModel.messageToShowAfterLinking.value  = UiText.StringResource(R.string.empty_string)
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = stringResource(
                        id = R.string.welcome_message,
                        " ${currentUser.username}"
                    )
                )
            }
        }
    }




    when (val signOutResponse = viewModel.signOutResponse) {
        is ResponseGoogle.Loading -> Unit
        is ResponseGoogle.Failure -> signOutResponse.apply {
            //Log.d("signoutFailure", "Home: ${e.message}")
        }

        is ResponseGoogle.Success -> {
            val isUserSignedOut = signOutResponse.data
            LaunchedEffect(key1 = isUserSignedOut) {
                isUserSignedOut?.let {
                    if (it) {
                        navController.navigate("signIn_Screen")
                    }
                }

            }
        }
    }
}