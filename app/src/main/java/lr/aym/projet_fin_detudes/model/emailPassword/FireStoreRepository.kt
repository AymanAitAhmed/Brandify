package lr.aym.projet_fin_detudes.model.emailPassword

import com.google.firebase.auth.FirebaseUser
import lr.aym.projet_fin_detudes.model.User

typealias addUserResponse = AddToFirestoreResponse<Boolean>
typealias getUserResponse = AddToFirestoreResponse<User?>

interface FireStoreRepository {
    val currentUser:FirebaseUser?
    suspend fun addUserToFireStore(user: User):addUserResponse

    suspend fun getUserFromFireStore():getUserResponse
}