package lr.aym.projet_fin_detudes.views.addPostScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.SignOut
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import lr.aym.projet_fin_detudes.model.posting.Post
import lr.aym.projet_fin_detudes.model.posting.cloudStorage.CloudStorageRepository
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerRepository
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val cloudStorageRepo: CloudStorageRepository,
    private val localServerRepository: LocalServerRepository,
    private val auth: FirebaseAuth,
    private val signOut: SignOut
) : ViewModel() {

    val descriptionTextField = mutableStateOf("")

    val descriptionError = mutableStateOf(UiText.StringResource(R.string.empty_string))

    val doneButtonActivated = mutableStateOf(false)

    private val _onDoneDialogState = MutableStateFlow<OnDoneDialogState>(OnDoneDialogState.Closed)
    val onDoneDialogState = _onDoneDialogState.asStateFlow()

    val postImages = mutableStateOf<List<Uri>>(emptyList())

    val logUserOut = mutableStateOf(false)

    val showSignedOutDialog = mutableStateOf(false)


    fun isButtonActivated() {
        Log.d("isButtonActivated", "executed")
        if (descriptionTextField.value.isNotEmpty() && postImages.value.isNotEmpty()) {
            if (postImages.value.size > 5) {
                descriptionError.value = UiText.StringResource(R.string.error_to_many_images)
                doneButtonActivated.value = false
            } else {
                descriptionError.value = UiText.StringResource(R.string.empty_string)
                doneButtonActivated.value = true
            }
        } else {
            doneButtonActivated.value = false
        }

    }

    fun onDialogDismiss() {
        _onDoneDialogState.value = OnDoneDialogState.Closed
        descriptionTextField.value = ""
        postImages.value = emptyList()
    }

    fun sendNewPostForReview() {
        viewModelScope.launch {
            doneButtonActivated.value = false
            _onDoneDialogState.value = OnDoneDialogState.Loading
            val userExistenceResponse = auth.currentUser?.uid?.let {
                localServerRepository.checkUserExistence(it)
            }
            when (userExistenceResponse) {
                is Response.Failure -> userExistenceResponse.apply {
                    if (e is HttpException && e.code() == 401) {
                        logUserOut.value = true
                        Log.d("sendNewPostForReview", "Unauthorized")
                    }
                }

                is Response.Loading -> TODO()
                is Response.Success -> {
                    val uploadImageResponse = auth.currentUser?.uid?.let {
                        cloudStorageRepo.uploadImagesToCloud(postImages.value, it)
                    }
                    when (uploadImageResponse) {
                        is Response.Failure -> uploadImageResponse.apply {
                            _onDoneDialogState.value = OnDoneDialogState.Failure("${e.message}")
                            doneButtonActivated.value = true
                            Log.d("sendingPost", "Upload image to firebase error: $e")
                        }

                        is Response.Loading -> {

                        }

                        is Response.Success -> {
                            val imagesUrls = uploadImageResponse.data
                            Log.d("sendingPost", "Upload image to firebase success: $imagesUrls")
                            val postToSend = auth.currentUser?.uid?.let {
                                Post(
                                    userUID = it,
                                    message = descriptionTextField.value,
                                    DownLoadUrls = imagesUrls
                                )
                            }
                            val sendingPostResponse = postToSend?.let {
                                localServerRepository.sendPostForReview(it)
                            } ?: Response.Failure(Exception("Post is empty"))

                            when (sendingPostResponse) {
                                is Response.Failure -> sendingPostResponse.apply {
                                    _onDoneDialogState.value =
                                        OnDoneDialogState.Failure("${e.message}")
                                    doneButtonActivated.value = true
                                    Log.d("sendingPost", "sendNewPostForReview error: $e ")
                                }

                                is Response.Loading -> {

                                }

                                is Response.Success -> {
                                    _onDoneDialogState.value = OnDoneDialogState.Success
                                    descriptionTextField.value = ""
                                    imagesUrls.clear()
                                    doneButtonActivated.value = true
                                    Log.d(
                                        "sendingPost",
                                        "sendNewPostForReview success: ${sendingPostResponse.data} "
                                    )
                                }
                            }


                        }

                        null -> {
                            _onDoneDialogState.value =
                                OnDoneDialogState.Failure("No user was detected")
                        }
                    }
                }

                null -> TODO()
            }

        }
    }

    fun signOut(): Boolean {
        val signOutResponse = signOut.signOut()
        return when (signOutResponse) {
            is ResponseGoogle.Failure -> {
                false
            }

            is ResponseGoogle.Loading -> false
            is ResponseGoogle.Success -> {
                signOutResponse.data ?: false
            }
        }
    }

}