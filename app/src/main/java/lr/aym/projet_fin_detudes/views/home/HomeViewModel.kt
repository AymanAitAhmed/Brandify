package lr.aym.projet_fin_detudes.views.home

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
import lr.aym.projet_fin_detudes.model.posting.UserProfileInfo
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerRepository
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val signOut: SignOut,
    private val localServerRepository: LocalServerRepository
) :ViewModel() {

    private val _userProfileInfo = MutableStateFlow(
        UserProfileInfo(emptyList(), "", "", "", "", 0, 0, 0, 0, 0, 0, 0, emptyList())
    )
    val userProfileInfo = _userProfileInfo.asStateFlow()

    val loadingPosts = mutableStateOf(false)

    private val _snackBarText = MutableStateFlow(UiText.StringResource(R.string.empty_string))
    val snackBarText = _snackBarText.asStateFlow()

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val isScrolingUp = mutableStateOf(false)

    val logUserOut = mutableStateOf(false)

    val showSignedOutDialog = mutableStateOf(false)
    val showDeletePostLoading = mutableStateOf(false)



    init {
        getUserProfile()
    }

    val messageToShowAfterLinking = MutableStateFlow(UiText.StringResource(R.string.empty_string))

     fun getUserProfile(){
       auth.currentUser?.uid?.let {
           _isRefreshing.value = true
           viewModelScope.launch {
               val response = localServerRepository.getUserProfileInfo(it)
               when(response){
                   is Response.Failure ->response.apply {
                       if (e is HttpException && e.code() == 401){
                           logUserOut.value = true
                       }
                       _isRefreshing.value = false
                       Log.d("getUserProfileInfo", "error: $e")
                   }
                   is Response.Loading -> TODO()
                   is Response.Success -> {
                       _isRefreshing.value = false
                       _userProfileInfo.value = response.data
                   }
               }
           }
       }
    }

    fun deletePostFromFb(postId : String){
        showDeletePostLoading.value = true
        viewModelScope.launch {
            val deletePostFromFbResponse = localServerRepository.deletePostFromFb(postId)
            when(deletePostFromFbResponse){
                is Response.Failure -> deletePostFromFbResponse.apply{
                    showDeletePostLoading.value = false
                    _snackBarText.value = UiText.StringResource(R.string.error_deleting_post)
                    Log.d("deletePostFromFb", "Failure : $e")
                }
                is Response.Loading -> TODO()
                is Response.Success ->deletePostFromFbResponse.apply {
                    if (data){
                        getUserProfile()
                        showDeletePostLoading.value = false
                        _snackBarText.value  = UiText.StringResource(R.string.post_deleted)
                    }
                }
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