package lr.aym.projet_fin_detudes.model.posting.cloudStorage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import lr.aym.projet_fin_detudes.model.emailPassword.FirestoreResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudStorageRepositoryImpl @Inject constructor(
    private val storage : FirebaseStorage
) : CloudStorageRepository {
    override suspend fun uploadImagesToCloud(images: List<Uri>,userUID : String): AddImagesToCloudResponse {

        return try {
            val imagesCloudUrls = mutableListOf<String>()
            images.forEach { image ->
                imagesCloudUrls.add(
                    storage.reference.child("images").child("$userUID-${System.currentTimeMillis()}")
                        .putFile(image).await()
                        .storage.downloadUrl.await().toString()
                )
            }
            return FirestoreResponse.Success(imagesCloudUrls)
        }catch (e : Exception){
            FirestoreResponse.Failure(e)
        }
    }

}