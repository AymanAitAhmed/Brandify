package lr.aym.projet_fin_detudes

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import lr.aym.projet_fin_detudes.components.Screens
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.AuthStateResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val emailPasswordAuthRepository: EmailPasswordAuthRepository
):ViewModel() {

    val doesUserExist = mutableStateOf(false)
    val startDestination = mutableStateOf("")
    var isEmailVerified by mutableStateOf(false)

    suspend fun getStartDestination(){
        getAuthState()
        isEmailVerifed()
        checkUserExistence()
        startDestination.value = if (getAuthState().value){
            Screens.SignInScreen.route
        }else{
            if (isEmailVerified){
                if (doesUserExist.value){
                    Log.d("navigatedFrom", "MainActivity")
                    Screens.HomeScreen.route
                }else{
                    Log.d("userExist", "does user existe: ${doesUserExist.value}")
                    Screens.AdditionalInfoScreen.route
                }

            }else{
                Screens.VerifyEmailScreen.route
            }
        }
    }
    suspend fun isEmailVerifed(){
            isEmailVerified = emailPasswordAuthRepository.isEmailVerified()
    }
    fun getAuthState(): AuthStateResponse {
        return emailPasswordAuthRepository.getAuthState(viewModelScope)
    }

    suspend fun checkUserExistence(){
            doesUserExist.value = emailPasswordAuthRepository.isUserInfoExist()
    }




}