package lr.aym.projet_fin_detudes.model.posting

data class UnreviewedPosts(
    val id: Int?,
    val userUID: String?,
    val message: String?,
    val imagesUrls: List<String>? = null
)
