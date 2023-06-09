package lr.aym.projet_fin_detudes.model.posting

data class UpdateUserResponse(
    val updated : Boolean,
    val errorMessage:String? = null
)