package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(auth : FirebaseAuth, database : FirebaseFirestore) :
            AuthRepository = AuthRepositoryImpl(auth, database)

    @Provides
    @Singleton
    fun provideProfileRepository(
        auth : FirebaseAuth,
        database: FirebaseFirestore
    ) :
            ProfileRepository = ProfileRepositoryImpl(auth,database)

    @Provides
    @Singleton
    fun provideDiseaseRepository(database: FirebaseFirestore) : DiseaseRepository = DiseaseRepositoryImpl(database)
}