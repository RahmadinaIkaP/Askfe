package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.Constant.Companion.USER
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val auth : FirebaseAuth,
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
        auth.currentUser?.let { user ->
            database.collection(USER).document(user.uid)
                .set(user)
                .addOnSuccessListener {
                    response.invoke(ResponseState.Success("Ubah profil berhasil!"))
                }
                .addOnFailureListener {
                    response.invoke(ResponseState.Error(it.localizedMessage!!))
                }
        }
    }

    override fun updatePassword(password: String, response: (ResponseState<String>) -> Unit) {
        auth.currentUser?.updatePassword(password)
            ?.addOnCompleteListener {
                updatePasswordDatabase(password){
                    when(it){
                        is ResponseState.Error -> {
                            response.invoke(ResponseState.Error(it.msg))
                        }
                        is ResponseState.Loading -> {}
                        is ResponseState.Success -> {
                            response.invoke(ResponseState.Success("Ubah password berhasil!"))
                        }
                    }
                }
            }
            ?.addOnFailureListener {
                response.invoke(ResponseState.Error(it.localizedMessage!!))
            }
    }

    override fun updatePasswordDatabase(
        password: String,
        response: (ResponseState<String>) -> Unit
    ) {
        auth.currentUser?.let {
            database.collection(USER).document(it.uid)
                .update("password", password)
                .addOnSuccessListener {
                    response.invoke(ResponseState.Success("berhasil mengubah password di database!"))
                }
                .addOnFailureListener { e ->
                    response.invoke(ResponseState.Error(e.localizedMessage!!))
                }
        }
    }

}