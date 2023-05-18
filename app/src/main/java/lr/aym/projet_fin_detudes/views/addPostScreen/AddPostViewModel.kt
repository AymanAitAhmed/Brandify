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
import lr.aym.projet_fin_detudes.components.UiText
import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.emailPassword.FirestoreResponse
import lr.aym.projet_fin_detudes.model.posting.Post
import lr.aym.projet_fin_detudes.model.posting.cloudStorage.CloudStorageRepository
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerRepository
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val cloudStorageRepo: CloudStorageRepository,
    private val localServerRepository: LocalServerRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    val descriptionTextField = mutableStateOf("")

    val descriptionError = mutableStateOf(UiText.StringResource(R.string.empty_string))

    val doneButtonActivated = mutableStateOf(false)

    private val _onDoneDialogState = MutableStateFlow<OnDoneDialogState>(OnDoneDialogState.Closed)
    val onDoneDialogState = _onDoneDialogState.asStateFlow()

    val postImages = mutableStateOf<List<Uri>>(emptyList())

    fun isButtonActivated() {
        doneButtonActivated.value =
            descriptionTextField.value.isNotEmpty() && postImages.value.isNotEmpty()
    }

    fun onDialogDismiss(){
        _onDoneDialogState.value = OnDoneDialogState.Closed
        descriptionTextField.value = ""
        postImages.value = emptyList()
    }

    fun sendNewPostForReview() {
        viewModelScope.launch {
            doneButtonActivated.value = false
            _onDoneDialogState.value = OnDoneDialogState.Loading
            val uploadImageResponse = auth.currentUser?.uid?.let {
                cloudStorageRepo.uploadImagesToCloud(postImages.value, it)
            }
            when (uploadImageResponse) {
                is FirestoreResponse.Failure -> uploadImageResponse.apply {
                    _onDoneDialogState.value = OnDoneDialogState.Failure("${e.message}")
                    doneButtonActivated.value = true
                    Log.d("sendingPost", "Upload image to firebase error: $e")
                }

                is FirestoreResponse.Loading -> {

                }

                is FirestoreResponse.Success -> {
                    val imagesUrls = uploadImageResponse.data
                    Log.d("sendingPost", "Upload image to firebase success: $imagesUrls")
                    val postToSend = auth.currentUser?.uid?.let {
                        Post(
                            userUID = it,
                            message = descriptionTextField.value,
                            DownLoadUrls = imagesUrls.toString()
                        )
                    }
                    val sendingPostResponse = postToSend?.let {
                        localServerRepository.sendPostForReview(it)
                    } ?: Response.Failure(Exception("Post is empty"))

                    when (sendingPostResponse) {
                        is Response.Failure -> sendingPostResponse.apply {
                            _onDoneDialogState.value = OnDoneDialogState.Failure("${e.message}")
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
                    _onDoneDialogState.value = OnDoneDialogState.Failure("No user was detected")
                }
            }
        }
    }


}