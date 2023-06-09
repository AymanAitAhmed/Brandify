package lr.aym.projet_fin_detudes.model.posting.localServerApi

import lr.aym.firebaseAdminSDK.UpdateUserBody
import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.posting.Post
import lr.aym.projet_fin_detudes.model.posting.PostDetailsFromFb
import lr.aym.projet_fin_detudes.model.posting.UnreviewedPostsReceived
import lr.aym.projet_fin_detudes.model.posting.UpdateUserResponse
import lr.aym.projet_fin_detudes.model.posting.UserProfileInfo

typealias getPostsFromFbResponse = Response<List<PostDetailsFromFb>>
typealias getUnreviewPostsResponse = Response<List<UnreviewedPostsReceived>>
typealias getUserProfileInfoResponse = Response<UserProfileInfo>
typealias deletePostFromFbResponse = Response<Boolean>
typealias addUserInfoResponse = Response<UpdateUserResponse>
typealias checkUserExistenceResponse = Response<Boolean>

interface LocalServerRepository {

    suspend fun checkUserExistence(userUID: String) : checkUserExistenceResponse
    suspend fun sendPostForReview(post: Post) : postToLocalServerResponse

    suspend fun getPostsFromFb(userUID : String) : getPostsFromFbResponse

    suspend fun getUserInReviewPosts(userUID: String) : getUnreviewPostsResponse

    suspend fun getUserProfileInfo(userUID: String) : getUserProfileInfoResponse

    suspend fun deletePostFromFb(postId : String) : deletePostFromFbResponse

    suspend fun addUserInfo(userUID: String,updateUserBody: UpdateUserBody) : addUserInfoResponse
}