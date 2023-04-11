package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

interface AuthRepository {
    fun register(email : String, password : String, user: User, response: (ResponseState<String>) -> Unit)
    fun login(email: String, password: String, response: (ResponseState<String>) -> Unit)
    fun addUserToDatabase(user: User, response: (ResponseState<String>) -> Unit)
    fun logout(response: () -> Unit)
}