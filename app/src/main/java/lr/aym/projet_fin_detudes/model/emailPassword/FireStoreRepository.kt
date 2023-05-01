package lr.aym.projet_fin_detudes.model.emailPassword

import com.google.firebase.auth.FirebaseUser
import lr.aym.projet_fin_detudes.model.User

typealias addUserResponse = FirestoreResponse<Boolean>
typealias getUserResponse = FirestoreResponse<User?>
typealias userExistResponse = FirestoreResponse<Boolean>

interface FireStoreRepository {
    val currentUser:FirebaseUser?
    suspend fun addUserToFireStore(user: User):addUserResponse

    suspend fun getUserFromFireStore():getUserResponse

    suspend fun checkUserExistenceFireStore():userExistResponse


}