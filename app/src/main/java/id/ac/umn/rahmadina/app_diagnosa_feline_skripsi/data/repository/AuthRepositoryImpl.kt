package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.Constant.Companion.USER
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

class AuthRepositoryImpl(
    private val auth : FirebaseAuth,
    private val database : FirebaseFirestore
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
                }else{
                    ResponseState.Error("Register gagal!")
                }
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(it.localizedMessage!!)
                )
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

    override fun logout(response: () -> Unit) {
        auth.signOut()
        response.invoke()
    }

    override fun getUser(email: String, response: (ResponseState<List<User>>) -> Unit) {
        database.collection(USER)
            .whereEqualTo("email",email)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val user = arrayListOf<User>()
                for (document in it){
                    val obj = document.toObject(User::class.java)
                    user.add(obj)
                }
                response.invoke(
                    ResponseState.Success(user)
                )
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(
                        it.localizedMessage!!
                    )
                )
            }
    }

    override fun login(
        email: String,
        password: String,
        response: (ResponseState<String>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful){
                    response.invoke(ResponseState.Success("Login berhasil!"))
                }
            }
            .addOnFailureListener {
                response.invoke(ResponseState.Error("Login gagal"))
            }
    }
}