package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.collection.arrayMapOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.*
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.ActivityHasilDiagnosisBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.hide
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.MainActivity
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.AuthActivity
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis.viewmodel.DiagnosisViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.infopenyakit.viewmodel.DiseaseViewModel
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HasilDiagnosisActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHasilDiagnosisBinding
    private lateinit var sharedPref : SharedPref
    private val vmDiagnosis : DiagnosisViewModel by viewModels()
    private val vmPenyakit : DiseaseViewModel by viewModels()
    private val hasilCfTotal : ArrayList<CfTotal> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilDiagnosisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        val bundle = intent.extras
        val dataHasil = bundle?.getParcelableArrayList<InputCfUser>("hasilUser") as ArrayList<InputCfUser>
        val dataGejala = bundle.getStringArrayList("gejala")

        mulaiDiagnosa(dataGejala, dataHasil)

        binding.apply {
            btnDiagnoseAgain.setOnClickListener {
                val intentDa = Intent(applicationContext, KonsultasiActivity::class.java)
                startActivity(intentDa)
                finish()
            }

            btnBackToHome.setOnClickListener {
                val intentBth = Intent(applicationContext, MainActivity::class.java)
                startActivity(intentBth)
                finish()
            }
        }
    }

    private fun mulaiDiagnosa(
        gejala: ArrayList<String>?,
        hasil: ArrayList<InputCfUser>
    ) {
        if (gejala?.isEmpty() == true){
            getPenyakitKosong()
        }else{
            gejala?.let { vmDiagnosis.getRulesSymptoms(it) }
            vmDiagnosis.getRulesObserver().observe(this){ response ->
                when(response){
                    is ResponseState.Error -> {
                        Log.e("HasilDiagnosisFragment", response.msg)
                    }
                    is ResponseState.Loading -> {
                        Log.d("HasilDiagnosisFragment", "loading data")
                    }
                    is ResponseState.Success -> {
                        Log.d("HasilDiagnosisFragment-DariUser", hasil.toString())
                        Log.d("HasilDiagnosisFragment-Rules", response.data.toString())
                        perhitunganCfTotal(response.data, hasil)
                    }
                }
            }
        }
    }

    private fun perhitunganCfTotal(data: List<Rules>, hasil: ArrayList<InputCfUser>) {
        data.forEach { rules ->
            hasil.forEach { result ->
                when (rules.id_penyakit) {
                    "F01" -> {
                        perhitunganCfUserPakar(rules.id_gejala, result.id_gejala, result.cf_user, rules.nilai_cf, rules.id_penyakit)
                    }
                    "F02" -> {
                        perhitunganCfUserPakar(rules.id_gejala, result.id_gejala, result.cf_user, rules.nilai_cf, rules.id_penyakit)
                    }
                    "F03" -> {
                        perhitunganCfUserPakar(rules.id_gejala, result.id_gejala, result.cf_user, rules.nilai_cf, rules.id_penyakit)
                    }
                    "F04" -> {
                        perhitunganCfUserPakar(rules.id_gejala, result.id_gejala, result.cf_user, rules.nilai_cf, rules.id_penyakit)
                    }
                    "F05" -> {
                        perhitunganCfUserPakar(rules.id_gejala, result.id_gejala, result.cf_user, rules.nilai_cf, rules.id_penyakit)
                    }
                }
            }
        }
        getCfTotalUserPakar(hasilCfTotal)
    }

    private fun perhitunganCfUserPakar(
        idGejalaRules: String?,
        idGejalaResult: String?,
        cfUser: Double?,
        cfPakar: Double,
        idPenyakit: String
    ) {
        var cfTotal = 0.0
        while (idGejalaRules == idGejalaResult) {
            cfTotal = cfUser!! * cfPakar
            hasilCfTotal.add(CfTotal(idPenyakit, cfTotal))
            Log.d("HasilCFTotal", hasilCfTotal.toString())
            break
        }
    }

    private fun getCfTotalUserPakar(
        hasilCfTotal: ArrayList<CfTotal>
    ) {
        var key1 = 0
        var key2 = 0
        var key3 = 0
        var key4 = 0
        var key5 = 0

        val listF01 = arrayMapOf<Int,Double>() // list untuk cf hipotesa f01
        val listF02 = arrayMapOf<Int,Double>() // list untuk cf hipotesa f02
        val listF03 = arrayMapOf<Int,Double>() // list untuk cf hipotesa f03
        val listF04 = arrayMapOf<Int,Double>() // list untuk cf hipotesa f04
        val listF05 = arrayMapOf<Int,Double>() // list untuk cf hipotesa f05

        var cfComb1 = 0.0 // cf untuk f01
        var cfComb2 = 0.0 // cf untuk f02
        var cfComb3 = 0.0 // cf untuk f03
        var cfComb4 = 0.0 // cf untuk f04
        var cfComb5 = 0.0 // cf untuk f05

        hasilCfTotal.forEach {
            when(it.id_penyakit){
                "F01" ->{
                    listF01[key1++] = it.cf_total!!
                    for (i in listF01){
                        if (i.key == 0){
                            cfComb1 = i.value
                        }else{
                            cfComb1 += i.value * (1.0 - cfComb1)
                        }
                    }
                }
                "F02" ->{
                    listF02[key2++] = it.cf_total!!
                    for (i in listF02){
                        if (i.key == 0){
                            cfComb2 = i.value
                        }else{
                            cfComb2 += i.value * (1.0 - cfComb2)
                        }
                    }
                }
                "F03" ->{
                    listF03[key3++] = it.cf_total!!
                    for (i in listF03){
                        if (i.key == 0){
                            cfComb3 = i.value
                        }else{
                            cfComb3 += i.value * (1.0 - cfComb3)
                        }
                    }
                }
                "F04" ->{
                    listF04[key4++] = it.cf_total!!
                    for (i in listF04){
                        if (i.key == 0){
                            cfComb4 = i.value
                        }else{
                            cfComb4 += i.value * (1.0 - cfComb4)
                        }
                    }
                }
                "F05" ->{
                    listF05[key5++] = it.cf_total!!
                    for (i in listF05){
                        if (i.key == 0){
                            cfComb5 = i.value
                        }else{
                            cfComb5 += i.value * (1.0 - cfComb5)
                        }
                    }
                }
            }
        }

        Log.d("HasilCFKombinasi", "$cfComb1, $cfComb2, $cfComb3, $cfComb4, $cfComb5")

        val listHasilCf = arrayListOf(
            Hasil("F01", cfComb1),
            Hasil("F02", cfComb2),
            Hasil("F03", cfComb3),
            Hasil("F04", cfComb4),
            Hasil("F05", cfComb5)
        )

        diagnosaFinal(listHasilCf)
    }

    @SuppressLint("SetTextI18n")
    private fun diagnosaFinal(listHasilCf: ArrayList<Hasil>) {

        val cfTertinggi = listHasilCf.maxBy { it.hasilCf!! }
        val nCfPersen = cfTertinggi.hasilCf!! * 100
        Log.d("Hasil-Final", cfTertinggi.toString())

        getPenyakit(cfTertinggi, nCfPersen)

    }

    @SuppressLint("SetTextI18n")
    private fun getPenyakit(penyakit: Hasil, nCfPersen: Double) {
        val df = DecimalFormat("#.##")
        val dCfPersen = df.format(nCfPersen)
        val namaKucing = intent.extras?.getString("nKucing")

        vmPenyakit.getDiseaseInfo()
        vmPenyakit.diseaseObserver().observe(this){ response ->
            when(response){
                is ResponseState.Error -> {
                    binding.progressBar.hide()
                }
                is ResponseState.Loading -> {
                    binding.progressBar.show()
                }
                is ResponseState.Success -> {
                    binding.progressBar.hide()
                    response.data.forEach {
                        if (it.id == penyakit.id_penyakit){
                            binding.apply {
                                val builder = StringBuilder()

                                if (nCfPersen <= 70.0){
                                    tvLblPenyakit.text = "Kucing anda yang bernama $namaKucing berisiko terkena penyakit:"
                                }else{
                                    tvLblPenyakit.text = "Kucing anda yang bernama $namaKucing terkena penyakit:"
                                }

                                tvHasilPenyakit.text = it.nama_penyakit
                                tvNilaiPersen.text = "$dCfPersen%"
                                tvPenjelasan.text = it.deskripsi
                                for (solusi in it.solusi){
                                    builder.append(solusi).append("\n")
                                }
                                tvSolusi.text = builder.toString()
                            }
                            addToRiwayat(it.nama_penyakit, dCfPersen, it.deskripsi, it.solusi, namaKucing)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getPenyakitKosong(){
        val namaKucing = intent.extras?.getString("nKucing")
        val arr = arrayListOf("Jaga selalu kesehatan kucing dengan memberi vitamin dan makan cukup, dan jangan lupa berikan kucing anda vaksin!")
        binding.apply {
            tvLblPenyakit.text = "Kucing anda yang bernama $namaKucing didiagnosis penyakit: "
            tvHasilPenyakit.text = "Tidak ada, sehat"
            tvLblNilaiPersen.hide()
            tvNilaiPersen.hide()
            tvLblPenjelasan.hide()
            tvPenjelasan.hide()
            tvSolusi.text = "Jaga selalu kesehatan kucing dengan memberi vitamin dan makan cukup, dan jangan lupa berikan kucing anda vaksin!"
        }
        addToRiwayat(
            "Tidak ada, sehat",
            "0",
            "-",
            arr,
            namaKucing
        )
    }

    private fun addToRiwayat(
        namaPenyakit: String?,
        nCfPersen: String,
        deskripsi: String?,
        solusi: List<String?>,
        namaKucing: String?
    ) {
        sharedPref.getUid.asLiveData().observe(this){ uid ->
            vmDiagnosis.addToHistory(
                History(
                id = "",
                idUser = uid,
                namaKucing = namaKucing,
                hdPenyakit = namaPenyakit,
                hdCfPersen = nCfPersen,
                hdDeskripsi = deskripsi,
                hdSolusi = solusi,
                tglKonsultasi = Date()
            )
            )
        }
        vmDiagnosis.addHistoryObserver().observe(this){
            when(it){
                is ResponseState.Error -> {
                    Log.e("Tambah Data", it.msg)
                }
                is ResponseState.Loading -> {

                }
                is ResponseState.Success -> {
                    Log.d("Tambah Data", it.data)
                }
            }
        }
    }
}