package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.*
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

interface DiseaseRepository {
    fun showListDisease(response: (ResponseState<List<Penyakit>>) -> Unit)
    fun showQuestion(response: (ResponseState<List<Gejala>>) -> Unit)
    fun getGejalaRules(list : ArrayList<String>,response: (ResponseState<List<Rules>>) -> Unit)
    fun getGejalaChosen(list : ArrayList<String>,response: (ResponseState<List<Gejala>>) -> Unit)
    fun addToHistory(history: History, response: (ResponseState<String>) -> Unit)
}