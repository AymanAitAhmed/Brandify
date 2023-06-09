package lr.aym.projet_fin_detudes.model.posting.localServerApi

import lr.aym.firebaseAdminSDK.UpdateUserBody
import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.posting.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalServerRepositoryImpl @Inject constructor(
    private val localServerApi: LocalServerApi
) : LocalServerRepository {
    override suspend fun checkUserExistence(userUID: String): checkUserExistenceResponse {
        return try {
            localServerApi.checkUserExistence(userUID)
            Response.Success(true)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun sendPostForReview(post : Post) : postToLocalServerResponse {
        return try {
            localServerApi.sendPostForReview(post)
            Response.Success("post submitted successfully")
        }catch (e : Exception){
            Response.Failure(e)
        }
    }

    override suspend fun getPostsFromFb(userUID: String): getPostsFromFbResponse {
        return try {
            Response.Success(localServerApi.getUserPosts(userUID))
        }catch (e : Exception){
            Response.Failure(e)
        }
    }

    override suspend fun getUserInReviewPosts(userUID: String): getUnreviewPostsResponse {
        return try {
            Response.Success(localServerApi.getUserInReviewPosts(userUID))
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun getUserProfileInfo(userUID: String): getUserProfileInfoResponse {
        return try {
            Response.Success(localServerApi.getUserProfileInfo(userUID))
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun deletePostFromFb(postId: String): deletePostFromFbResponse {
        return try {
            localServerApi.deletePostFromFb(postId)
            Response.Success(true)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun addUserInfo(userUID: String, updateUserBody: UpdateUserBody) : addUserInfoResponse {
        return try {
            val response = localServerApi.addUserInfo(userUID,updateUserBody)
            Response.Success(response)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }
}