package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.arrayMapOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.*
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentHasilDiagnosisBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis.viewmodel.DiagnosisViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.infopenyakit.viewmodel.DiseaseViewModel
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HasilDiagnosisFragment : Fragment() {
    private var _binding: FragmentHasilDiagnosisBinding? = null
    private val binding get() = _binding!!
    private val vmDiagnosis : DiagnosisViewModel by viewModels()
    private val vmPenyakit : DiseaseViewModel by viewModels()
    private val hasilCfTotal : ArrayList<CfTotal> = arrayListOf()
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHasilDiagnosisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        val dataHasil = arguments?.getParcelableArrayList<InputCfUser>("hasilUser") as ArrayList<InputCfUser>
        val dataGejala = arguments?.getStringArrayList("gejala")

        mulaiDiagnosa(dataGejala, dataHasil)

        binding.apply {
            btnDiagnoseAgain.setOnClickListener {
                findNavController().navigate(R.id.action_hasilDiagnosisFragment_to_konfirmasiDiagnosisFragment)
            }

            btnBackToHome.setOnClickListener {
                findNavController().navigate(R.id.action_hasilDiagnosisFragment_to_homeFragment)
            }
        }
    }

    private fun mulaiDiagnosa(
        gejala: ArrayList<String>?,
        hasil: ArrayList<InputCfUser>
    ) {
        gejala?.let { vmDiagnosis.getRulesSymptoms(it) }
        vmDiagnosis.getRulesObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseState.Error -> {

                }
                is ResponseState.Loading -> {

                }
                is ResponseState.Success -> {
                    Log.d("HasilDiagnosisFragment-DariUser", hasil.toString())
                    Log.d("HasilDiagnosisFragment-Rules", response.data.toString())
                    perhitunganCfTotal(response.data, hasil)
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
        val df = DecimalFormat("#.##")

        val cfTertinggi = listHasilCf.maxBy { it.hasilCf!! }
        val nCfPersen = df.format(cfTertinggi.hasilCf!! * 100)
        Log.d("Hasil-Final", cfTertinggi.toString())

        getPenyakit(cfTertinggi, nCfPersen)

    }

    @SuppressLint("SetTextI18n")
    private fun getPenyakit(penyakit: Hasil, nCfPersen: String) {
        val namaKucing = arguments?.getString("nKucing")
        vmPenyakit.getDiseaseInfo()
        vmPenyakit.diseaseObserver().observe(viewLifecycleOwner){ response ->
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
                                var builder = StringBuilder()

                                tvLblPenyakit.text = "Kucing anda yang bernama $namaKucing kemungkinan terkena penyakit:"
                                tvHasilPenyakit.text = it.nama_penyakit
                                tvNilaiPersen.text = "$nCfPersen%"
                                tvPenjelasan.text = it.deskripsi
                                for (solusi in it.solusi){
                                    builder.append(solusi).append("\n")
                                }
                                tvSolusi.text = builder.toString()
                            }
                            addToRiwayat(it.nama_penyakit, nCfPersen, it.deskripsi, it.solusi, namaKucing)
                        }
                    }
                }
            }
        }
    }

    private fun addToRiwayat(
        namaPenyakit: String?,
        nCfPersen: String,
        deskripsi: String?,
        solusi: List<String?>,
        namaKucing: String?
    ) {
        sharedPref.getUid.asLiveData().observe(viewLifecycleOwner){ uid ->
            vmDiagnosis.addToHistory(History(
                id = "",
                idUser = uid,
                namaKucing = namaKucing,
                hdPenyakit = namaPenyakit,
                hdCfPersen = nCfPersen,
                hdDeskripsi = deskripsi,
                hdSolusi = solusi,
                tglKonsultasi = Date()
            ))
        }
        vmDiagnosis.addHistoryObserver().observe(viewLifecycleOwner){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}