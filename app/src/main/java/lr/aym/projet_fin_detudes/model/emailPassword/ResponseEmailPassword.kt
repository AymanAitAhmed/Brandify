package lr.aym.projet_fin_detudes.model.emailPassword

sealed class ResponseEmailPassword<out T>{
    object Loading: ResponseEmailPassword<Nothing>()

    data class Success<out T>(
        val data: T
    ): ResponseEmailPassword<T>()
    data class Failure(
        val e : Exception
    ): ResponseEmailPassword<Nothing>()
}
