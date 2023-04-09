package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository.AuthRepository
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository.AuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(auth : FirebaseAuth, database : FirebaseFirestore) :
            AuthRepository = AuthRepositoryImpl(auth, database)
}