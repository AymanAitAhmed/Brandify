package lr.aym.projet_fin_detudes.model.posting.cloudStorage

import android.net.Uri
import lr.aym.projet_fin_detudes.model.Response

typealias AddImagesToCloudResponse = Response<MutableList<String>>

interface CloudStorageRepository {

suspend fun uploadImagesToCloud(images : List<Uri>, userUID : String) : AddImagesToCloudResponse

}