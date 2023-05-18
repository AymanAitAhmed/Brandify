package lr.aym.projet_fin_detudes.di

import android.app.Application
import android.content.Context
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import lr.aym.projet_fin_detudes.R
import lr.aym.projet_fin_detudes.components.Constants
import lr.aym.projet_fin_detudes.components.SignOut
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepository
import lr.aym.projet_fin_detudes.model.emailPassword.EmailPasswordAuthRepositoryImpl
import lr.aym.projet_fin_detudes.model.google.GoogleAuthRepository
import lr.aym.projet_fin_detudes.model.google.GoogleAuthRepositoryImpl
import lr.aym.projet_fin_detudes.model.ProfileRepository
import lr.aym.projet_fin_detudes.model.ProfileRepositoryImpl
import lr.aym.projet_fin_detudes.model.emailPassword.FireStoreRepository
import lr.aym.projet_fin_detudes.model.emailPassword.FireStoreRepositoryImpl
import lr.aym.projet_fin_detudes.model.facebook.FacebookAuthRepository
import lr.aym.projet_fin_detudes.model.facebook.FacebookAuthRepositoryImpl
import lr.aym.projet_fin_detudes.model.posting.cloudStorage.CloudStorageRepository
import lr.aym.projet_fin_detudes.model.posting.cloudStorage.CloudStorageRepositoryImpl
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerApi
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerRepository
import lr.aym.projet_fin_detudes.model.posting.localServerApi.LocalServerRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth


    @Provides
    fun provideSignOut(
        profileRepository: ProfileRepository,
        facebookAuthRepository: FacebookAuthRepository
    ): SignOut = SignOut(profileRepository, facebookAuthRepository)

    @Provides
    fun provideEmailAuthRepository(): EmailPasswordAuthRepository = EmailPasswordAuthRepositoryImpl(
        auth = provideFirebaseAuth(),
        fireStoreRepository = provideFirestoreRepository()
    )

    @Provides
    fun provideFirestoreRepository(): FireStoreRepository = FireStoreRepositoryImpl(
        db = provideFirebaseFirestore(),
        auth = provideFirebaseAuth()
    )

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideFirebaseStorage() = Firebase.storage

    @Provides
    fun provideCloudStorageRepository(): CloudStorageRepository = CloudStorageRepositoryImpl(
        storage = provideFirebaseStorage()
    )

    @Provides
    fun provideLocalServerApi(): LocalServerApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(LocalServerApi::class.java)

    }

    @Provides
    fun provideLocalServerRepository():LocalServerRepository =
        LocalServerRepositoryImpl(
            localServerApi = provideLocalServerApi()
        )

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named("SIGN_IN_REQUEST")
    fun provideSignInRequest(
        application: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(application.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(false)
        .build()


    @Provides
    @Named("SIGN_UP_REQUEST")
    fun provideSignUpRequest(
        application: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(application.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()


    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideGoogleAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named("SIGN_IN_REQUEST")
        signInRequest: BeginSignInRequest,
        @Named("SIGN_UP_REQUEST")
        signUpRequest: BeginSignInRequest,
        db: FirebaseFirestore
    ): GoogleAuthRepository = GoogleAuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        db = db
    )

    @Provides
    fun provideProfileRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient
    ): ProfileRepository = ProfileRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
    )

    //Facebook

    @Provides
    fun provideLoginManager() = LoginManager.getInstance()

    @Provides
    fun provideCallBackManager() = CallbackManager.Factory.create()

    @Provides
    fun provideFacebookRepo(
        loginManager: LoginManager,
        callbackManager: CallbackManager,
        auth: FirebaseAuth
    ): FacebookAuthRepository = FacebookAuthRepositoryImpl(
        loginManager = loginManager,
        callbackManager = callbackManager,
        auth = auth
    )


}