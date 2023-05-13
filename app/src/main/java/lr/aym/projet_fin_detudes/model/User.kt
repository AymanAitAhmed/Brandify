package lr.aym.projet_fin_detudes.model

import android.net.Uri

data class User(
    val UID:String? = null,
    val username : String? =null,
    val email:String? = null,
    val phoneNumber:String? = null,
    val profilePicture:Uri? = null,
    val dateOfBirth:String? = null,
    val gender:String? = null,
)
