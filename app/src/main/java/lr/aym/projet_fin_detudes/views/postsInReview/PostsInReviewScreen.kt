package lr.aym.projet_fin_detudes.views.postsInReview

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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.SignedOutDialog

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostsInReviewScreen(
    viewModel: PostsInReviewViewModel = hiltViewModel(),
    navController: NavController
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scaffoldState = rememberScaffoldState()
    val unreviewedPosts by viewModel.unreviewedPostsList.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.getUserInReviewPosts() })

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
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            PostsInReviewTopBar(onBackClick = {
                navController.popBackStack()
            },
                onSortListClick = {

                })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .pullRefresh(refreshState)
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            if (unreviewedPosts.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(unreviewedPosts) { post ->

                        PostCardInReview(
                            userUID = post.userUID,
                            message = post.message,
                            imagesUrls = post.imagesUrls
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                        )

                    }

                }
            } else {
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
                                text = stringResource(id = R.string.no_posts_in_review),
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

