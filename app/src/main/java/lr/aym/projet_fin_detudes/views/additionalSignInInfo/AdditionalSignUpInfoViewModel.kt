package lr.aym.projet_fin_detudes.views.additionalSignInInfo

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.model.User
import lr.aym.projet_fin_detudes.model.emailPassword.FirestoreResponse
import lr.aym.projet_fin_detudes.model.emailPassword.FireStoreRepository
import lr.aym.projet_fin_detudes.model.emailPassword.addUserResponse
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AdditionalSignUpInfoViewModel @Inject constructor(
    private val fireStoreRepo: FireStoreRepository
) : ViewModel() {

    var username = mutableStateOf("")
    var usernameErrorMessage = mutableStateOf("")
    var phoneNumber = mutableStateOf("")
    var phoneNumberErrorMessage = mutableStateOf("")
    var dateOfBirth = mutableStateOf("")
    var dateOfBirthErrorMessage = mutableStateOf("")
    var gender = mutableStateOf("")
    var genderErrorMessage = mutableStateOf("")
    var showLoadingState = mutableStateOf(false)

    var addUserResponse by mutableStateOf<addUserResponse>(
        FirestoreResponse.Success(false)
    )

    val errorMessage = mutableStateOf("")


    fun usernameChecks():Boolean{
        return if (username.value.isBlank()){
            usernameErrorMessage.value = "Please enter a username"
            false
        }else if (username.value.length < 3){
            usernameErrorMessage.value = "Username must be at least 3 characters"
            false
        }else{
            usernameErrorMessage.value = ""
            true
        }
    }

    fun phoneNumberChecks():Boolean{
        return if (phoneNumber.value.isBlank()){
            phoneNumberErrorMessage.value = "Please enter a phone number"
            false
        }else if (phoneNumber.value.length != 10){
            phoneNumberErrorMessage.value = "Phone number must have exacly 10 digits"
            false
        }else{
            phoneNumberErrorMessage.value = ""
            true
        }
    }

    fun dateChecks():Boolean{
        if (dateOfBirth.value.isBlank()){
            dateOfBirthErrorMessage.value = "Please enter a your date of birth"
            return false
        }else{
            // Parse the birth date string into a Calendar object
            val birthDate = Calendar.getInstance()
            birthDate.time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateOfBirth.value)

            // Calculate the user's age
            val today = Calendar.getInstance()
            var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) age--

            // Check if the user is 18 years or older
            if (age < 16){
                dateOfBirthErrorMessage.value="Too young. You have to be at least 16 years old"
                return false
            }
            else{
                dateOfBirthErrorMessage.value = ""
                return true
            }
        }

    }
    fun genderChecks():Boolean{
        return if (gender.value.isBlank()){
            genderErrorMessage.value = "Please select your Gender"
            false
        }else{
            genderErrorMessage.value = ""
            true
        }
    }
    fun addUserToFireStore() {
        addUserResponse = FirestoreResponse.Loading
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