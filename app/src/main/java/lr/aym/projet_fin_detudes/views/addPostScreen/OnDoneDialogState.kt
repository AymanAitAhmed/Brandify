package lr.aym.projet_fin_detudes.views.addPostScreen


sealed class OnDoneDialogState{

    object Closed : OnDoneDialogState()
    object Loading : OnDoneDialogState()
    object Success : OnDoneDialogState()
    data class Failure(val errorMessage : String) : OnDoneDialogState()

}
