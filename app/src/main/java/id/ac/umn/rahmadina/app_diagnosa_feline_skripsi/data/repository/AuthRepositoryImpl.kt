package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.Constant.Companion.USER
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

class AuthRepositoryImpl(
    val auth : FirebaseAuth,
    val database : FirebaseFirestore
) : AuthRepository {
    override fun register(
        email: String,
        password: String,
        user: User,
        response: (ResponseState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful){
                    addUserToDatabase(user){ responses ->
                        when(responses){
                            is ResponseState.Success ->{
                                response.invoke(ResponseState.Success("Register berhasil!"))
                            }
                            is ResponseState.Error -> {
                                response.invoke(ResponseState.Error(responses.msg))
                            }
                            ResponseState.Loading -> {}
                        }
                    }
                }
            }
            .addOnFailureListener {

            }
    }

    override fun addUserToDatabase(user: User, response: (ResponseState<String>) -> Unit) {
        val document = database.collection(USER).document()
        user.id = document.id
        document.set(user)
            .addOnSuccessListener {
                response.invoke(
                    ResponseState.Success("Berhasil menndaftarkan pengguna baru")
                )
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(it.localizedMessage!!)
                )
            }
    }

    override fun login() {
        TODO("Not yet implemented")
    }
}