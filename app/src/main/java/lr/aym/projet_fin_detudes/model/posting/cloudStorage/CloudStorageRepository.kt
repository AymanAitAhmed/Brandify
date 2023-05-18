package lr.aym.projet_fin_detudes.model.posting.cloudStorage

import android.net.Uri
import lr.aym.projet_fin_detudes.model.emailPassword.FirestoreResponse

typealias AddImagesToCloudResponse = FirestoreResponse<MutableList<String>>

interface CloudStorageRepository {

suspend fun uploadImagesToCloud(images : List<Uri>, userUID : String) : AddImagesToCloudResponse

}