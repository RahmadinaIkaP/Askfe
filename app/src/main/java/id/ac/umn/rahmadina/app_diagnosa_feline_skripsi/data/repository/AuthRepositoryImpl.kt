package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
                    auth.currentUser?.let {
                        addUserToDatabase(user, it.uid){ responses ->
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

    override fun addUserToDatabase(user: User, id : String, response: (ResponseState<String>) -> Unit) {
        val document = database.collection(USER).document(id)
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

    override fun login(
        email: String,
        password: String,
        response: (ResponseState<String>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful){
                    getUser{ result2 ->
                        when(result2){
                            is ResponseState.Error -> {
                                response.invoke(ResponseState.Error(result2.msg))
                            }
                            is ResponseState.Success -> {
                                response.invoke(ResponseState.Success("Login berhasil!"))
                            }
                            ResponseState.Loading -> {}
                        }
                    }
                }
            }
            .addOnFailureListener {
                response.invoke(ResponseState.Error("Login gagal"))
            }
    }

    override fun logout(response: () -> Unit) {
        auth.signOut()
        response.invoke()
    }

    override fun getUser(response: (ResponseState<List<User>>) -> Unit) {
        database.collection(USER)
            .whereEqualTo("id", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener {
                val users = arrayListOf<User>()

                for (document in it) {
                    val user = document.toObject(User::class.java)
                    users.add(user)
                }
                response.invoke(
                    ResponseState.Success(users)
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
}