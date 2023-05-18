package lr.aym.projet_fin_detudes.model.posting.localServerApi

import lr.aym.projet_fin_detudes.model.Response
import lr.aym.projet_fin_detudes.model.posting.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalServerRepositoryImpl @Inject constructor(
    private val localServerApi: LocalServerApi
) : LocalServerRepository {
    override suspend fun sendPostForReview(post : Post) : postToLocalServerResponse {
        return try {
            localServerApi.sendPostForReview(post)
            Response.Success("post submitted successfully")
        }catch (e : Exception){
            Response.Failure(e)
        }
    }
}