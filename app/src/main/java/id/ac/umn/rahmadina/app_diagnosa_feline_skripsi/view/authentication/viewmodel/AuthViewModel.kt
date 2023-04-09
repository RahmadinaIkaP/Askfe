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

    fun registerObserver() : MutableLiveData<ResponseState<String>> = registerLiveData

    fun register(email : String, password : String, user: User){
        registerLiveData.value = ResponseState.Loading
        repository.register(email, password, user){
            registerLiveData.value = it
        }
    }
}