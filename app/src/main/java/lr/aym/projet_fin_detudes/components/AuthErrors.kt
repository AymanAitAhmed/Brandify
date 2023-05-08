package lr.aym.projet_fin_detudes.components


const val EMAIL_USED = "This email is already used."
const val PASSWORD_WEAK = "The password is too weak"
const val INVALID_EMAIL = "The email is invalid."
const val PASSWORD_WRONG = "The password is wrong."
const val NONE_EXISTING_EMAIL ="There is no account with the corresponding email."
const val ACCOUNT_DISABLED = "Your account has been temporally disabled"
const val TOO_MANY_ATTEMPTS = "Too many attempts, try again later or reset your password."

fun authErrors(message :String?): String {
    when(message){
        "The email address is already in use by another account." ->{
            return EMAIL_USED
        }
        "The given password is invalid. [ Password should be at least 6 characters ]" ->{
            return PASSWORD_WEAK
        }
        "The email address is badly formatted." ->{
            return INVALID_EMAIL
        }
        "The password is invalid or the user does not have a password." ->{
            return  PASSWORD_WRONG
        }
        "There is no user record corresponding to this identifier. The user may have been deleted." ->{
            return NONE_EXISTING_EMAIL
        }
        "The user account has been disabled by an administrator." ->{
            return ACCOUNT_DISABLED
        }
        "We have blocked all requests from this device due to unusual activity. Try again later. [ Access to this account has been temporarily disabled due to many failed login attempts. You can immediately restore it by resetting your password or you can try again later. ]" ->{
            return TOO_MANY_ATTEMPTS
        }
        else ->{
            return ""
        }

    }
}