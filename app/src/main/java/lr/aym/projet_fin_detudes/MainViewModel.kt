package lr.aym.projet_fin_detudes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.AuthStateResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val emailPasswordAuthRepository: EmailPasswordAuthRepository
):ViewModel() {

    val doesUserExist = mutableStateOf(false)
    val startDestination = mutableStateOf("")
    init {

        getAuthState()
        checkUserExistence()
    }
    val isEmailVerified = emailPasswordAuthRepository.isEmailVerified()

    fun getAuthState(): AuthStateResponse {
        return emailPasswordAuthRepository.getAuthState(viewModelScope)
    }

    fun checkUserExistence(){
        viewModelScope.launch {
            doesUserExist.value = emailPasswordAuthRepository.isUserInfoExist()
        }
    }




}