package lr.aym.projet_fin_detudes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.AuthStateResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: EmailPasswordAuthRepository
):ViewModel() {

    init {
        getAuthState()
    }
    val isEmailVerified = repository.currentUser?.isEmailVerified?:false

    fun getAuthState(): AuthStateResponse {
        return repository.getAuthState(viewModelScope)
    }


}