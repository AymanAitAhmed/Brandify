package lr.aym.projet_fin_detudes.model.posting.localServerApi

import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.posting.Post
import retrofit2.http.Body
import retrofit2.http.POST

typealias postToLocalServerResponse = Response<String>

interface LocalServerApi {

    @POST("review/posts")
    suspend fun sendPostForReview(@Body post: Post)

}