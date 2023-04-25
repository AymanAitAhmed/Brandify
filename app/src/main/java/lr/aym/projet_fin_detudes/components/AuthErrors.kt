package lr.aym.projet_fin_detudes.components

fun authErrors(message :String?): String {
    when(message){
        "The email address is already in use by another account." ->{
            return "This email is already used."
        }
        "The given password is invalid. [ Password should be at least 6 characters ]" ->{
            return "The password is too weak"
        }
        "The email address is badly formatted." ->{
            return "The email is invalid."
        }
        "The password is invalid or the user does not have a password." ->{
            return  "The password is wrong."
        }
        "There is no user record corresponding to this identifier. The user may have been deleted." ->{
            return "The email is not used by any account."
        }
        "The user account has been disabled by an administrator." ->{
            return "Your account has been temporally disabled"
        }
        "We have blocked all requests from this device due to unusual activity. Try again later. [ Access to this account has been temporarily disabled due to many failed login attempts. You can immediately restore it by resetting your password or you can try again later. ]" ->{
            return "Too many attempts, try again later or reset your password."
        }
        else ->{
            return ""
        }

    }
}