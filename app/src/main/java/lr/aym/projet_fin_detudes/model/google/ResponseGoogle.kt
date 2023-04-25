package lr.aym.projet_fin_detudes.model.google

sealed class ResponseGoogle<out T>{
    object Loading: ResponseGoogle<Nothing>()

    data class Success<out T>(
        val data: T?
    ): ResponseGoogle<T>()
    data class Failure(
        val e : Exception
    ): ResponseGoogle<Nothing>()
}