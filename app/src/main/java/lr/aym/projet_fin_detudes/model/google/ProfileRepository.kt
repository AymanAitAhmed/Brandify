package lr.aym.projet_fin_detudes.model.google


typealias SignOutResponse = ResponseGoogle<Boolean>


interface ProfileRepository {

    fun signOut(): SignOutResponse
}