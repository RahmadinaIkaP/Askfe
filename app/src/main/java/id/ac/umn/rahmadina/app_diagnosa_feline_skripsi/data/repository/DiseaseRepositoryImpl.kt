package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Penyakit
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

class DiseaseRepositoryImpl(
    private val database : FirebaseFirestore
) : DiseaseRepository {

    override fun showListDisease(response: (ResponseState<List<Penyakit>>) -> Unit) {
        database.collection("penyakit").get()
            .addOnSuccessListener {
                val diseases = arrayListOf<Penyakit>()
                for (document in it){
                    val disease = document.toObject(Penyakit::class.java)
                    diseases.add(disease)
                }
                response.invoke(
                    ResponseState.Success(diseases)
                )
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(it.localizedMessage!!)
                )
            }
    }
}