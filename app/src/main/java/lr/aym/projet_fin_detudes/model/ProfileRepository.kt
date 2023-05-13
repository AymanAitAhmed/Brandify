package lr.aym.projet_fin_detudes.model

import lr.aym.projet_fin_detudes.model.google.ResponseGoogle

typealias SignOutResponse = ResponseGoogle<Boolean>

interface ProfileRepository {

    fun signOut(): SignOutResponse
}