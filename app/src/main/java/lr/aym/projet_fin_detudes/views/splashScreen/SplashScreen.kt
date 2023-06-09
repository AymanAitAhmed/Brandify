package lr.aym.projet_fin_detudes.views.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import lr.aym.projet_fin_detudes.MainViewModel
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.Screens

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    LaunchedEffect(key1 = 1){
        delay(500L)
        viewModel.getStartDestination()
        navController.navigate(viewModel.startDestination.value){
            popUpTo(Screens.SplashScreen.route){
                inclusive=true
            }
        }
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(350.dp)
        )
    }

}