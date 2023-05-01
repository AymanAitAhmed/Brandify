package lr.aym.projet_fin_detudes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.AuthStateResponse
import lr.aym.projet_fin_detudes.model.emailPassword.FireStoreRepository
import lr.aym.projet_fin_detudes.model.emailPassword.FirestoreResponse
import lr.aym.projet_fin_detudes.model.emailPassword.userExistResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val emailPasswordAuthRepository: EmailPasswordAuthRepository,
    private val fireStoreRepository: FireStoreRepository
):ViewModel() {

    init {
        getAuthState()
    }
    val isEmailVerified = emailPasswordAuthRepository.currentUser?.isEmailVerified?:false

    var userExistResponse by mutableStateOf<userExistResponse>(FirestoreResponse.Success(false))
    fun getAuthState(): AuthStateResponse {
        return emailPasswordAuthRepository.getAuthState(viewModelScope)
    }
    fun isUserInfoExist() : Boolean{
        var userExist = false
        viewModelScope.launch {
          userExistResponse = fireStoreRepository.checkUserExistenceFireStore()

            when(val userExistResponse = userExistResponse){
                is FirestoreResponse.Loading ->{

                }
                is FirestoreResponse.Success ->{
                    if (userExistResponse.data){
                        userExist = true
                    }
                }
                is FirestoreResponse.Failure ->{

                }
            }
        }
        return userExist
    }


}