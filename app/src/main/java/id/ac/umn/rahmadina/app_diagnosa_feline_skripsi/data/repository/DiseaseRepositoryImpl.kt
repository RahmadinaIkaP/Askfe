package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Gejala
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.History
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Penyakit
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Rules
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.Constant.Companion.HISTORY
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

    override fun showQuestion(response: (ResponseState<List<Gejala>>) -> Unit) {
        database.collection("gejala").get()
            .addOnSuccessListener {
                val symptoms = arrayListOf<Gejala>()
                for (document in it){
                    val symptom = document.toObject(Gejala::class.java)
                    symptoms.add(symptom)
                }
                response.invoke(
                    ResponseState.Success(symptoms)
                )
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(it.localizedMessage!!)
                )
            }
    }

    override fun getGejalaRules(
        list: ArrayList<String>,
        response: (ResponseState<List<Rules>>) -> Unit
    ) {
        database.collection("rules")
            .whereIn("id_gejala", list)
            .orderBy("id", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener{
                val rules = arrayListOf<Rules>()
                for (document in it){
                    val rule = document.toObject(Rules::class.java)
                    rules.add(rule)
                }
                response.invoke(
                    ResponseState.Success(rules)
                )
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(it.localizedMessage!!)
                )
            }
    }

    override fun getGejalaChosen(
        list: ArrayList<String>,
        response: (ResponseState<List<Gejala>>) -> Unit
    ) {
        database.collection("gejala")
            .whereIn("id", list)
            .get()
            .addOnSuccessListener{
                val symptoms = arrayListOf<Gejala>()
                for (document in it){
                    val symptom = document.toObject(Gejala::class.java)
                    symptoms.add(symptom)
                }
                response.invoke(
                    ResponseState.Success(symptoms)
                )
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(it.localizedMessage!!)
                )
            }
    }

    override fun addToHistory(history: History, response: (ResponseState<String>) -> Unit) {
        val document = database.collection(HISTORY).document()
        history.id = document.id
        document.set(history)
            .addOnSuccessListener {
                response.invoke(
                    ResponseState.Success("data berhasil masuk ke history")
                )
            }
            .addOnFailureListener {
                response.invoke(
                    ResponseState.Error(it.localizedMessage!!)
                )
            }
    }
}