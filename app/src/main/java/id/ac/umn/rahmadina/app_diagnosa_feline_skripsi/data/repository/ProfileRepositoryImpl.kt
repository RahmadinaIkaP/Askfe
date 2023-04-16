package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val storageRef : StorageReference,
    private val database : FirebaseFirestore
) : ProfileRepository {

    override suspend fun updateImgProfile(fileUri: Uri, response: (ResponseState<Uri>) -> Unit) {
        try {
            val uri : Uri = withContext(Dispatchers.IO){
                storageRef
                    .putFile(fileUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }
            response.invoke(ResponseState.Success(uri))
        }catch (e : FirebaseException){
            response.invoke(ResponseState.Error(e.message.toString()))
        }catch (e : Exception){
            response.invoke(ResponseState.Error(e.message.toString()))
        }
    }

    override fun editProfile(user: User, response: (ResponseState<String>) -> Unit) {

    }

}