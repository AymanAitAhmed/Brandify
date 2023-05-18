package lr.aym.projet_fin_detudes.model.posting.localServerApi

import lr.aym.projet_fin_detudes.model.posting.Post

interface LocalServerRepository {

    suspend fun sendPostForReview(post: Post) : postToLocalServerResponse
}