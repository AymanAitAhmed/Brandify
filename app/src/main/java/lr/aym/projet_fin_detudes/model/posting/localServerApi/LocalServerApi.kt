package lr.aym.projet_fin_detudes.model.posting.localServerApi

import lr.aym.firebaseAdminSDK.UpdateUserBody
import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.posting.Post
import lr.aym.projet_fin_detudes.model.posting.PostDetailsFromFb
import lr.aym.projet_fin_detudes.model.posting.UnreviewedPostsReceived
import lr.aym.projet_fin_detudes.model.posting.UpdateUserResponse
import lr.aym.projet_fin_detudes.model.posting.UserProfileInfo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

typealias postToLocalServerResponse = Response<String>

interface LocalServerApi {

    @GET("allusers/{user_uid}")
    suspend fun checkUserExistence(@Path("user_uid") userUID : String)

    @POST("review/posts")
    suspend fun sendPostForReview(@Body post: Post)

    @GET("approved/posts/{user_uid}")
    suspend fun getUserPosts(@Path("user_uid") userUID : String) : List<PostDetailsFromFb>

    @GET("review/posts/{user_uid}")
    suspend fun getUserInReviewPosts(@Path("user_uid") userUID: String) : List<UnreviewedPostsReceived>

    @GET("allusers/profile/{user_uid}")
    suspend fun getUserProfileInfo(@Path("user_uid") userUID: String) : UserProfileInfo

    @DELETE("approved/posts/{post_id}")
    suspend fun deletePostFromFb(@Path("post_id") postId : String)

    @PUT("allusers/{user_uid}")
    suspend fun addUserInfo(@Path("user_uid")userUID: String , @Body updateUserBody: UpdateUserBody): UpdateUserResponse
}