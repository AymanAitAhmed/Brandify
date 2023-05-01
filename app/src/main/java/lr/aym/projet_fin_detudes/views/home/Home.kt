package lr.aym.projet_fin_detudes.views.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import lr.aym.projet_fin_detudes.views.verifyEmail.VerifiyEmailViewModel

@Composable
fun Home(
    viewModel: VerifiyEmailViewModel= hiltViewModel(),
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(text = stringResource(id = R.string.welcome_message,"${viewModel.getUser()}" ))
            Button(onClick = {
                viewModel.signOut()
            }) {
                Text(text = stringResource(id = R.string.Sign_Out))
            }
        }
    }
    when(val signOutResponse = viewModel.signOutResponse){
        is ResponseGoogle.Loading -> Unit
        is ResponseGoogle.Failure -> signOutResponse.apply {
            //Log.d("signoutFailure", "Home: ${e.message}")
        }
        is ResponseGoogle.Success -> {
            val isUserSignedOut = signOutResponse.data
            LaunchedEffect(key1 = isUserSignedOut ){
                isUserSignedOut?.let {
                    if(it){
                      navController.navigate("signIn_Screen")
                    }
                }

            }
        }
    }
}