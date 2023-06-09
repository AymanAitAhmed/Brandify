package lr.aym.projet_fin_detudes.model.posting

data class UnreviewedPostsReceived(
    val id : Int?,
    val userUID : String?,
    val message : String?,
    val imagesUrls : String? = null
)
