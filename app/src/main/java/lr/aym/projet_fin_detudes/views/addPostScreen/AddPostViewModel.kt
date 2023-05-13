package lr.aym.projet_fin_detudes.views.addPostScreen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.UiText

class AddPostViewModel:ViewModel() {

    val descriptionTextField = mutableStateOf("")

    val descriptionError = mutableStateOf(UiText.StringResource(R.string.empty_string))

    val doneButtonActivated = mutableStateOf(false)

    val postImages = mutableStateOf<List<Uri>>(emptyList())

    fun isButtonActivated(){
        doneButtonActivated.value = descriptionTextField.value.isNotEmpty()&& postImages.value.isNotEmpty()
    }
}