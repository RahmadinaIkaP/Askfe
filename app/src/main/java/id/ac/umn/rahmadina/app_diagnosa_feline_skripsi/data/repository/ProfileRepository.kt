package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import android.net.Uri
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

interface ProfileRepository {
    suspend fun updateImgProfile(fileUri : Uri, response : (ResponseState<Uri>) -> Unit)
    fun editProfile(user: User, response: (ResponseState<String>) -> Unit)
}