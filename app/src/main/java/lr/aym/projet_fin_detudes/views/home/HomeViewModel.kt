package lr.aym.projet_fin_detudes.views.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.SignOut
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.SignOutResponse
import lr.aym.projet_fin_detudes.model.User
import lr.aym.projet_fin_detudes.model.emailPassword.FireStoreRepository
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStoreRepository: FireStoreRepository,
    private val signOut: SignOut
) :ViewModel() {
    private val _currentUser = MutableStateFlow(User())
    val currentUser = _currentUser.asStateFlow()

    init {
        getCurrentUser()
    }

    var signOutResponse by mutableStateOf<SignOutResponse>(ResponseGoogle.Success(false))

    val messageToShowAfterLinking = MutableStateFlow(UiText.StringResource(R.string.empty_string))

    private fun getCurrentUser(){
        viewModelScope.launch {
            _currentUser.value = fireStoreRepository.getCurrentUser()
        }
    }

    fun signOut() {
        signOutResponse = signOut.signOut()
    }

}