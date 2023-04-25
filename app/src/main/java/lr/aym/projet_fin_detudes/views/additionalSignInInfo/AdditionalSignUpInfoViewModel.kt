package lr.aym.projet_fin_detudes.views.additionalSignInInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.model.User
import lr.aym.projet_fin_detudes.model.emailPassword.AddToFirestoreResponse
import lr.aym.projet_fin_detudes.model.emailPassword.FireStoreRepository
import lr.aym.projet_fin_detudes.model.emailPassword.addUserResponse
import javax.inject.Inject

@HiltViewModel
class AdditionalSignUpInfoViewModel @Inject constructor(
    private val fireStoreRepo: FireStoreRepository
) : ViewModel() {

    var username = mutableStateOf("")
    var phoneNumber = mutableStateOf("")
    var dateOfBirth = mutableStateOf("")
    var gender = mutableStateOf("")
    var showLoadingState = mutableStateOf(false)

    var addUserResponse by mutableStateOf<addUserResponse>(
        AddToFirestoreResponse.Success(false)
    )

    val errorMessage = mutableStateOf("")


    fun addUserToFireStore() {
        addUserResponse = AddToFirestoreResponse.Loading
        viewModelScope.launch {
            addUserResponse = fireStoreRepo.addUserToFireStore(
                User(
                    UID = fireStoreRepo.currentUser?.uid,
                    username = username.value,
                    email = fireStoreRepo.currentUser?.email,
                    phoneNumber = phoneNumber.value,
                    dateOfBirth = dateOfBirth.value,
                    gender = gender.value
                )
            )
        }
    }

}