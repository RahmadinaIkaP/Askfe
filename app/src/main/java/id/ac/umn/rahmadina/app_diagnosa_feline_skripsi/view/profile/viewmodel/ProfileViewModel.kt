package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository.ProfileRepository
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val repository: ProfileRepository
) : ViewModel() {
    private var ubahPassLiveData : MutableLiveData<ResponseState<String>> = MutableLiveData()
    private var updatePassLiveData : MutableLiveData<ResponseState<String>> = MutableLiveData()
    private var updateProfileLiveData : MutableLiveData<ResponseState<String>> = MutableLiveData()

    fun ubahPassObserver() : MutableLiveData<ResponseState<String>> = ubahPassLiveData
    fun updatePassObserver() : MutableLiveData<ResponseState<String>> = updatePassLiveData
    fun updateProfileObserver() : MutableLiveData<ResponseState<String>> = updateProfileLiveData

    fun ubahPassword(password : String){
        ubahPassLiveData.value = ResponseState.Loading
        repository.updatePassword(password){
            ubahPassLiveData.value = it
        }
    }

    fun updatePassDatabase(password: String){
        updatePassLiveData.value = ResponseState.Loading
        repository.updatePasswordDatabase(password){
            updatePassLiveData.value = it
        }
    }

    fun updateProfile(email : String, name : String, gender : String, ttl : String, img : String ){
        updateProfileLiveData.value = ResponseState.Loading
        repository.editProfile(email, name, gender, ttl, img){
            updateProfileLiveData.value = it
        }
    }

    fun uploadImgProfile(fileUri: Uri, file: File,result: (ResponseState<Uri>) -> Unit){
        result.invoke(ResponseState.Loading)
        viewModelScope.launch {
            repository.updateImgProfile(fileUri, file ,result)
        }
    }
}