package lr.aym.projet_fin_detudes.model.emailPassword

import android.util.Log
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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
            user.UID?.let { UID ->
                db.collection("users").document(UID).set(user).await()
            }
            FirestoreResponse.Success(true)
        } catch (e: Exception) {
            FirestoreResponse.Failure(e)
        }

    }

    override suspend fun getUserFromFireStore(): getUserResponse {
        return try {
            val user =
                auth.currentUser?.uid?.let { UID ->
                    db.collection("users").document(UID).get().await()
                }
            FirestoreResponse.Success(auth.currentUser?.uid?.let {
                user?.toObject(User::class.java)?.copy(UID = it)
            })
        } catch (e: Exception) {
            FirestoreResponse.Failure(e)
        }
    }

    override suspend fun checkUserExistenceFireStore(): userExistResponse {

        return try {
            val user = auth.currentUser?.uid?.let { UID ->
                Log.d("userExist", "Uid to look for: $UID")
                db.collection("users").document(UID).get().await()
            }
            if (user?.exists() == true) {
                Log.d("userExist", "user exsistes")
                FirestoreResponse.Success(true)
            } else {
                Log.d("userExist", "user does not exist")
                FirestoreResponse.Success(false)
            }

        } catch (e: Exception) {
            FirestoreResponse.Failure(e)
        }

    }


    override suspend fun getCurrentUser(): User {
        var user = User()
        auth.currentUser?.let {
            for (userProvider in it.providerData) {
                when (userProvider.providerId) {
                    GoogleAuthProvider.PROVIDER_ID -> user = User(
                        UID = auth.currentUser?.uid,
                        username = auth.currentUser?.displayName,
                        email = auth.currentUser?.email,
                        phoneNumber = auth.currentUser?.phoneNumber,
                        profilePicture = auth.currentUser?.photoUrl
                    )

                    FacebookAuthProvider.PROVIDER_ID -> user = User(
                        UID = auth.currentUser?.uid,
                        username = auth.currentUser?.displayName,
                        email = auth.currentUser?.email,
                        phoneNumber = auth.currentUser?.phoneNumber,
                        profilePicture = auth.currentUser?.photoUrl
                    )

                    else -> {
                        when (val userFromFireStoreResponse = getUserFromFireStore()) {
                            is FirestoreResponse.Loading -> {

                            }

                            is FirestoreResponse.Success -> {
                                userFromFireStoreResponse.data?.let { userFromFireStore ->
                                    user = userFromFireStore
                                }
                            }

                            is FirestoreResponse.Failure -> {
                                user = User()
                            }
                        }
                    }
                }
            }

        }
        return user
    }
}