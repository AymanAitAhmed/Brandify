package lr.aym.projet_fin_detudes.model

import lr.aym.projet_fin_detudes.model.google.ResponseGoogle

typealias SignOutResponse = ResponseGoogle<Boolean>

interface ProfileRepository {
    val displayName: String
    val photoUrl: String

    suspend fun signOut(): SignOutResponse
}