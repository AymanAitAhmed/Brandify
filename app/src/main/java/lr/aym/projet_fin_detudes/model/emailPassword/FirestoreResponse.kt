package lr.aym.projet_fin_detudes.model.emailPassword

sealed class FirestoreResponse<out T>{
    object Loading: FirestoreResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): FirestoreResponse<T>()
    data class Failure(
        val e : Exception
    ): FirestoreResponse<Nothing>()
}
