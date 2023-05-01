package lr.aym.projet_fin_detudes.model.emailPassword

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import lr.aym.projet_fin_detudes.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FireStoreRepository {
    override val currentUser: FirebaseUser? = auth.currentUser

    override suspend fun addUserToFireStore(user: User): addUserResponse {
        return try {
            db.collection("users").add(user).await()
            FirestoreResponse.Success(true)
        } catch (e: Exception) {
            FirestoreResponse.Failure(e)
        }

    }

    override suspend fun getUserFromFireStore(): getUserResponse {
        return try {
            val user =
                auth.currentUser?.uid?.let { db.collection("users").document(it).get().await() }
            FirestoreResponse.Success(auth.currentUser?.uid?.let {
                user?.toObject(User::class.java)?.copy(UID = it)
            })
        } catch (e: Exception) {
            FirestoreResponse.Failure(e)
        }
    }

    override suspend fun checkUserExistenceFireStore(): userExistResponse {

        return try {
            val user = auth.currentUser?.uid?.let {
                db.collection("users").document(it).get().await()
            }
            if (user?.exists() == true){
                FirestoreResponse.Success(true)
            }
            else{
                FirestoreResponse.Success(false)
            }

        }catch (e:Exception){
            FirestoreResponse.Failure(e)
        }

    }
}