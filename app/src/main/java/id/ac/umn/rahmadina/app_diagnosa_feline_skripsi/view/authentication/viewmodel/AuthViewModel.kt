package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository.AuthRepository
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private var registerLiveData : MutableLiveData<ResponseState<String>> = MutableLiveData()
    private var loginLiveData : MutableLiveData<ResponseState<String>> = MutableLiveData()
    private var getUserLiveData : MutableLiveData<ResponseState<List<User>>> = MutableLiveData()

    fun registerObserver() : MutableLiveData<ResponseState<String>> = registerLiveData
    fun loginObserver() : MutableLiveData<ResponseState<String>> = loginLiveData
    fun getUserObserver() : MutableLiveData<ResponseState<List<User>>> = getUserLiveData

    fun register(email : String, password : String, user: User){
        registerLiveData.value = ResponseState.Loading
        repository.register(email, password, user){
            registerLiveData.value = it
        }
    }

    fun login(email: String, password: String){
        loginLiveData.value = ResponseState.Loading
        repository.login(email, password){
            loginLiveData.value = it
        }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

    fun getUser(){
        getUserLiveData.value = ResponseState.Loading
        repository.getUser{
            getUserLiveData.value = it
        }
    }
}