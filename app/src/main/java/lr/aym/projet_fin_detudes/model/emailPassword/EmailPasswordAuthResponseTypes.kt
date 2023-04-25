package lr.aym.projet_fin_detudes.model

import kotlinx.coroutines.flow.StateFlow
import lr.aym.projet_fin_detudes.model.emailPassword.ResponseEmailPassword

typealias SignUpResponse = ResponseEmailPassword<Boolean>
typealias SendEmailVerificationResponse = ResponseEmailPassword<Boolean>
typealias SignInResponse = ResponseEmailPassword<Boolean>
typealias ReloadUserResponse = ResponseEmailPassword<Boolean>
typealias SendPasswordResetEmailResponse = ResponseEmailPassword<Boolean>
typealias RevokeAccessResponse = ResponseEmailPassword<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>

