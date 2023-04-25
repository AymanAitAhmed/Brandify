package lr.aym.projet_fin_detudes.model.emailPassword

sealed class AddToFirestoreResponse<out T>{
    object Loading: AddToFirestoreResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): AddToFirestoreResponse<T>()
    data class Failure(
        val e : Exception
    ): AddToFirestoreResponse<Nothing>()
}
