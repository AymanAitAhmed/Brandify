package lr.aym.projet_fin_detudes.views.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.Screens
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import lr.aym.projet_fin_detudes.views.sign_in_up_process.verifyEmail.VerifiyEmailViewModel

@Composable
fun Home(
    viewModel: VerifiyEmailViewModel = hiltViewModel(),
    navController: NavController
) {

    val scafolldState = rememberScaffoldState()
    Scaffold(scaffoldState = scafolldState,
        topBar = {

        },
        drawerContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp)
                    .clickable {
                        viewModel.signOut()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_login_24),
                    contentDescription = null,
                    modifier = Modifier.rotate(180f),
                    tint = MaterialTheme.colors.primary
                )
                Divider(
                    modifier = Modifier
                        .height(10.dp)
                        .width(2.dp)
                        .padding(start = 4.dp, end = 4.dp)
                )
                Text(text = stringResource(id = R.string.Sign_Out))
            }
        },
    floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screens.AddPostScreen.route)
        }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colors.background)
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = stringResource(id = R.string.welcome_message, "${viewModel.getUser()}"))
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