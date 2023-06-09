package lr.aym.projet_fin_detudes.views.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.Screens
import lr.aym.projet_fin_detudes.components.SignedOutDialog
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.components.isScrollingUp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val scafolldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val userProfileInfo by viewModel.userProfileInfo.collectAsStateWithLifecycle()
    val userPosts = userProfileInfo.allPosts


    val linkingMessage =
        viewModel.messageToShowAfterLinking.collectAsStateWithLifecycle().value.asString()

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.getUserProfile() })

    LaunchedEffect(key1 = linkingMessage) {
        if (linkingMessage != "" && linkingMessage != "empty") {
            val snackBarResult =
                scafolldState.snackbarHostState.showSnackbar(message = linkingMessage)
            when (snackBarResult) {
                SnackbarResult.Dismissed -> viewModel.messageToShowAfterLinking.value =
                    UiText.StringResource(R.string.empty_string)

                SnackbarResult.ActionPerformed -> viewModel.messageToShowAfterLinking.value =
                    UiText.StringResource(R.string.empty_string)
            }
        }
    }

    LaunchedEffect(key1 = viewModel.logUserOut.value ){
        if (viewModel.logUserOut.value){
            val signOutResponse = viewModel.signOut()
            if (signOutResponse){
                viewModel.showSignedOutDialog.value = true
            }
        }
    }
    val state = rememberLazyListState()
    viewModel.isScrolingUp.value = state.isScrollingUp()

    if (viewModel.showSignedOutDialog.value){
        SignedOutDialog(navController = navController, showEmailSentDialog = viewModel.showSignedOutDialog)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scafolldState,
        topBar = {
            HomeTopBar(
                onMenuClick = {
                    scope.launch {
                        scafolldState.drawerState.open()
                    }
                },
                onSortListClick = {

                }
            )
        },
        drawerContent = {
            HomeDrawerContent(
                viewModel = viewModel,
                navController = navController,
                currentUser = userProfileInfo
            )
        },
        drawerScrimColor = if (isSystemInDarkTheme())Color(0x46000000) else Color(0x46FFFFFF),
        floatingActionButton = {
            Log.d("Home", "Home: ${viewModel.isScrolingUp.value}")
            AnimatedVisibility(visible = viewModel.isScrolingUp.value) {
                FloatingActionButton(onClick = {
                    navController.navigate(Screens.AddPostScreen.route)
                }, backgroundColor = MaterialTheme.colors.primary) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colors.background
                    )
                }
            }

        }) { paddingValues ->

        Box(
            modifier = Modifier
                .pullRefresh(refreshState)
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (userPosts.isNotEmpty()) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                    state = state
                ) {
                        item {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                            )
                        }

                        items(userPosts) { userPost ->
                            PostCard(
                                postDetailsFromFb = userPost,
                                username = userProfileInfo.username ?: ""
                            ) {
                                viewModel.deletePostFromFb(userPost.id)
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                            )
                        }
                    }
                    }else {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()){
                            if (viewModel.loadingPosts.value) {
                                item {
                                    CircularProgressIndicator(modifier = Modifier.size(90.dp))
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.fetching_your_posts),
                                        color = MaterialTheme.colors.onSecondary
                                    )

                                }
                            }else {
                                item {
                                    Text(
                                        text = stringResource(id = R.string.empty_list),
                                        style = MaterialTheme.typography.body1,
                                        color = MaterialTheme.colors.onSecondary,
                                        fontWeight = FontWeight.Bold
                                    )

                                }
                            }
                        }

                    }



            PullRefreshIndicator(
                isRefreshing,
                refreshState,
                Modifier.align(Alignment.TopCenter)
            )
            }
        }
}