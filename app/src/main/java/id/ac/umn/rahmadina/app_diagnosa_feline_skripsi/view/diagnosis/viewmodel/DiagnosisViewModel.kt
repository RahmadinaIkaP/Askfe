package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Gejala
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.History
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Rules
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository.DiseaseRepository
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import javax.inject.Inject

@HiltViewModel
class DiagnosisViewModel @Inject constructor(
    private val repository: DiseaseRepository
) : ViewModel() {
    private var getGejalaLiveData : MutableLiveData<ResponseState<List<Gejala>>> = MutableLiveData()
    private var getRulesLiveData : MutableLiveData<ResponseState<List<Rules>>> = MutableLiveData()
    private var addHistoryLiveData : MutableLiveData<ResponseState<String>> = MutableLiveData()
    private var getGejalaChosenLiveData : MutableLiveData<ResponseState<List<Gejala>>> = MutableLiveData()

    fun getGejalaObserver() : MutableLiveData<ResponseState<List<Gejala>>> = getGejalaLiveData
    fun getRulesObserver() : MutableLiveData<ResponseState<List<Rules>>> = getRulesLiveData
    fun addHistoryObserver() : MutableLiveData<ResponseState<String>> = addHistoryLiveData
    fun getGejalaChosenObserver() : MutableLiveData<ResponseState<List<Gejala>>> = getGejalaChosenLiveData

    fun getGejala(){
        getGejalaLiveData.value = ResponseState.Loading
        repository.showQuestion {
            getGejalaLiveData.value = it
        }
    }

    fun getRulesSymptoms(gejala : ArrayList<String>){
        getRulesLiveData.value = ResponseState.Loading
        repository.getGejalaRules(gejala){
            getRulesLiveData.value = it
        }
    }

    fun addToHistory(history: History){
        addHistoryLiveData.value = ResponseState.Loading
        repository.addToHistory(history){
            addHistoryLiveData.value = it
        }
    }

    fun getGejalaTerpilih(gejala: ArrayList<String>){
        getGejalaChosenLiveData.value = ResponseState.Loading
        repository.getGejalaChosen(gejala){
            getGejalaChosenLiveData.value = it
        }
    }
}