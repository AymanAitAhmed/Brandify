package lr.aym.projet_fin_detudes.views.sign_in_up_process.additionalSignInInfo

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.aym.firebaseAdminSDK.UpdateUserBody
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.SignOut
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.posting.UpdateUserResponse
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerRepository
import lr.aym.projet_fin_detudes.model.posting.localServerApi.addUserInfoResponse
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AdditionalSignUpInfoViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val localServerRepository: LocalServerRepository,
    private val signOut: SignOut
) : ViewModel() {

    var username = mutableStateOf("")
    var usernameErrorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))
    var phoneNumber = mutableStateOf("")
    var phoneNumberErrorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))
    var dateOfBirth = mutableStateOf("")
    var dateOfBirthErrorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))
    var gender = mutableStateOf("")
    var genderErrorMessage = mutableStateOf(UiText.StringResource(R.string.empty_string))
    var showLoadingState = mutableStateOf(false)

    var addUserInfoResponse by mutableStateOf<addUserInfoResponse>(Response.Success(UpdateUserResponse(false)))
    val errorMessage = mutableStateOf("")


    fun signout() {
        signOut.signOut()
    }

    fun usernameChecks(): Boolean {
        return if (username.value.isBlank()) {
            usernameErrorMessage.value = UiText.StringResource(id = R.string.empty_username_error)
            false
        } else if (username.value.length < 3) {
            usernameErrorMessage.value = UiText.StringResource(R.string.short_username_length)
            false
        } else {
            usernameErrorMessage.value = UiText.StringResource(R.string.empty_string)
            true
        }
    }

    fun phoneNumberChecks(): Boolean {
        return if (phoneNumber.value.isBlank()) {
            phoneNumberErrorMessage.value = UiText.StringResource(R.string.empty_phone_number)
            false
        } else if (phoneNumber.value.length != 9) {
            phoneNumberErrorMessage.value =
                UiText.StringResource(R.string.wrong_phone_number_length)
            false
        } else {
            phoneNumberErrorMessage.value = UiText.StringResource(R.string.empty_string)
            true
        }
    }

    fun dateChecks(): Boolean {
        if (dateOfBirth.value.isBlank()) {
            dateOfBirthErrorMessage.value = UiText.StringResource(R.string.empty_birthdate)
            return false
        } else {
            // Parse the birth date string into a Calendar object
            val birthDate = Calendar.getInstance()
            birthDate.time =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateOfBirth.value)

            // Calculate the user's age
            val today = Calendar.getInstance()
            var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) age--

            // Check if the user is 16 years or older
            if (age < 16) {
                dateOfBirthErrorMessage.value = UiText.StringResource(R.string.too_young)
                return false
            } else {
                dateOfBirthErrorMessage.value = UiText.StringResource(R.string.empty_string)
                return true
            }
        }

    }

    fun genderChecks(): Boolean {
        return if (gender.value.isBlank()) {
            genderErrorMessage.value = UiText.StringResource(R.string.empty_gender)
            false
        } else {
            genderErrorMessage.value = UiText.StringResource(R.string.empty_string)
            true
        }
    }

    fun addUserinfo() {
        viewModelScope.launch {
            auth.currentUser?.uid?.let { userUID ->
                addUserInfoResponse = localServerRepository.addUserInfo(
                    userUID, UpdateUserBody(
                        username = username.value,
                        phonenumber = "+212${ phoneNumber.value }"
                    )
                )
            }
        }
    }


}