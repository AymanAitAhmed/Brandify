package lr.aym.projet_fin_detudes.views.home

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AddPostViewModel:ViewModel() {

    val descriptionTextField = mutableStateOf("")

    val descriptionError = mutableStateOf("")

    val doneButtonActivated = mutableStateOf(false)

    val postImages = mutableStateOf<List<Uri>>(emptyList())

    fun isButtonActivated(){
        doneButtonActivated.value = descriptionTextField.value.isNotEmpty()&& postImages.value.isNotEmpty()
    }
}