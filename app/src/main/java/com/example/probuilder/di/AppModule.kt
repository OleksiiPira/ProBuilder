package com.example.probuilder.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.example.probuilder.data.local.AppDatabase
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import com.example.probuilder.data.remote.AppApi
import com.example.probuilder.data.remote.dto.AppRepositoryImpl
import com.example.probuilder.domain.repository.AppRepository
import com.example.probuilder.domain.repository.InvoiceDao
import com.example.probuilder.domain.use_case.auth.AccountService
import com.example.probuilder.domain.use_case.auth.GoogleAuthService
import com.example.probuilder.data.remote.CategoryService
import com.example.probuilder.data.remote.ClientService
import com.example.probuilder.data.remote.JobService
import com.example.probuilder.data.remote.ProjectService
import com.example.probuilder.data.remote.WorkerService
import com.example.probuilder.data.remote.impl.CategoryServiceImpl
import com.example.probuilder.data.remote.impl.ClientServiceImpl
import com.example.probuilder.data.remote.impl.JobServiceImpl
import com.example.probuilder.data.remote.impl.ProjectServiceImpl
import com.example.probuilder.data.remote.impl.WorkerServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppApi(): AppApi {
        return Retrofit.Builder()
            .baseUrl(com.example.probuilder.common.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(api: AppApi): AppRepository {
        return AppRepositoryImpl(api)
    }

    @Provides
    fun provideYourDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(context = appContext)
    }

    @Provides
    fun provideInvoiceDao(database: AppDatabase): InvoiceDao {
        return database.invoiceItemDao()
    }
    @Provides
    @Singleton
    fun provideInvoiceRepositoryImpl(dao: InvoiceDao): InvoiceRepositoryImpl {
        return InvoiceRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext appContext: Context): CredentialManager{
        return CredentialManager.create(appContext)
    }

    @Provides
    @Singleton
    fun provideAccountService(
        auth: FirebaseAuth,
        googleAuthService: GoogleAuthService,
        credentialManager: CredentialManager,
        @ApplicationContext appContext: Context): AccountService {
        return AccountService(auth, googleAuthService, credentialManager, appContext)
    }

    @Provides
    @Singleton
    fun provideGoogleAuthService(@ApplicationContext appContext: Context): GoogleAuthService {
        return GoogleAuthService(appContext)
    }

    @Provides
    @Singleton
    fun provideCategoryService(firestore: FirebaseFirestore, accountService: AccountService): CategoryService {
        return CategoryServiceImpl(firestore, accountService)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideJobService(firestore: FirebaseFirestore, accountService: AccountService): JobService {
        return JobServiceImpl(firestore, accountService)
    }

    @Provides
    @Singleton
    fun provideProjectService(firestore: FirebaseFirestore, accountService: AccountService): ProjectService {
        return ProjectServiceImpl(firestore, accountService)
    }

    @Provides
    @Singleton
    fun provideClientService(firestore: FirebaseFirestore, accountService: AccountService): ClientService {
        return ClientServiceImpl(firestore, accountService)
    }

    @Provides
    @Singleton
    fun provideWorkerService(firestore: FirebaseFirestore, accountService: AccountService): WorkerService {
        return WorkerServiceImpl(firestore, accountService)
    }

}