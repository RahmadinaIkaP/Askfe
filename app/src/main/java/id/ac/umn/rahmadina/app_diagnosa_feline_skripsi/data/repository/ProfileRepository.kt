package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import android.net.Uri
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import java.io.File

interface ProfileRepository {
    suspend fun updateImgProfile(fileUri : Uri, file: File ,response : (ResponseState<Uri>) -> Unit)
    fun updatePassword(password : String, response: (ResponseState<String>) -> Unit)
    fun updatePasswordDatabase(password: String, response: (ResponseState<String>) -> Unit)
    fun editProfile(email: String, name: String, gender: String, ttl: String, img: String, response: (ResponseState<String>) -> Unit)
}