package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Penyakit
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

interface DiseaseRepository {
    fun showListDisease(response: (ResponseState<List<Penyakit>>) -> Unit)
}