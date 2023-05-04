package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

//    @Provides
//    @Singleton
//    fun provideFirebaseStorage() : StorageReference = FirebaseStorage.getInstance().getReference("app/")
}