package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.infopenyakit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Penyakit
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository.DiseaseRepository
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import javax.inject.Inject

@HiltViewModel
class DiseaseViewModel @Inject constructor(
    private val repository : DiseaseRepository
) : ViewModel() {

    private var diseaseLiveData : MutableLiveData<ResponseState<List<Penyakit>>> = MutableLiveData()

    fun diseaseObserver() : MutableLiveData<ResponseState<List<Penyakit>>> = diseaseLiveData

    fun getDiseaseInfo(){
        diseaseLiveData.value = ResponseState.Loading
        repository.showListDisease{
            diseaseLiveData.value = it
        }
    }
}