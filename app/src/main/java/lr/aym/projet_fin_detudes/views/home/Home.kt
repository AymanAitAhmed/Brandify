package lr.aym.projet_fin_detudes.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import lr.aym.projet_fin_detudes.views.verifyEmail.VerifiyEmailViewModel

@Composable
fun Home(
    viewModel: VerifiyEmailViewModel= hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(text = "welcome ${viewModel.getUser()}")
            Button(onClick = {
                viewModel.signOut()
            }) {
                Text(text = "Sign out")
            }
        }
    }
}