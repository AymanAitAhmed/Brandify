package lr.aym.projet_fin_detudes.views.postsInReview

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
import lr.aym.projet_fin_detudes.components.convertPosts
import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.google.ResponseGoogle
import lr.aym.projet_fin_detudes.model.posting.UnreviewedPosts
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerRepository
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PostsInReviewViewModel @Inject constructor(
    private val localServerRepository: LocalServerRepository,
    private val auth: FirebaseAuth,
    private val signOut: SignOut
) : ViewModel() {

    private val _unreviewedPostsList = MutableStateFlow<List<UnreviewedPosts>>(emptyList())
    val unreviewedPostsList = _unreviewedPostsList.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _snackBarText = MutableStateFlow(UiText.StringResource(R.string.empty_string))
    val snackBarText = _snackBarText.asStateFlow()

    val loadingPosts = mutableStateOf(false)

    val logUserOut = mutableStateOf(false)

    val showSignedOutDialog = mutableStateOf(false)


    init {
        getUserInReviewPosts()
    }
    fun getUserInReviewPosts() {
        viewModelScope.launch {
            Log.d("getUnreviewedPosts", "started function")
            loadingPosts.value = true
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
                    val userUID = auth.currentUser?.uid
                    userUID?.let { user_uid ->
                        Log.d("getUnreviewedPosts", "user uid is not null")
                        val getUnreviewPostsResponse = localServerRepository.getUserInReviewPosts(user_uid)
                        when (getUnreviewPostsResponse) {
                            is Response.Failure -> getUnreviewPostsResponse.apply {
                                loadingPosts.value = false
                                _snackBarText.value = UiText.StringResource(R.string.getting_posts_error)
                                Log.d("getUnreviewedPosts", "error: $e")
                            }

                            is Response.Loading -> TODO()
                            is Response.Success -> {
                                Log.d("getUnreviewedPosts", "success")
                                _unreviewedPostsList.value = convertPosts(getUnreviewPostsResponse.data)
                                loadingPosts.value = false
                            }
                        }
                    }
                }
                null -> TODO()
            }

        }
    }

    fun signOut():Boolean{
        val signOutResponse = signOut.signOut()
        return when(signOutResponse){
            is ResponseGoogle.Failure -> {false}
            is ResponseGoogle.Loading -> false
            is ResponseGoogle.Success -> {
                signOutResponse.data ?: false
            }
        }
    }

}