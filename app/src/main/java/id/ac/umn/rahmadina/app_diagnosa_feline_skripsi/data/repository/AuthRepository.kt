package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

interface AuthRepository {
    fun register(email : String, password : String, user: User, response: (ResponseState<String>) -> Unit)
    fun login()
    fun addUserToDatabase(user: User, response: (ResponseState<String>) -> Unit)
}