package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Gejala
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.InputCfUser
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentPertanyaanBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.MainActivity
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.AuthActivity
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis.viewmodel.DiagnosisViewModel

@AndroidEntryPoint
class PertanyaanFragment : Fragment() {
    private var _binding : FragmentPertanyaanBinding? = null
    private val binding get() = _binding!!
    private val vmDiagnosis : DiagnosisViewModel by viewModels()
    private var list = arrayListOf<Gejala>()
    private var hasil = arrayListOf<InputCfUser>()
    private var counter = 0
    private var listGejala = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPertanyaanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGejala();
        binding.btnLanjut.setOnClickListener {
               setQuestion()
        }
    }

    private fun getGejala() {
        vmDiagnosis.getGejala()
        vmDiagnosis.getGejalaObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseState.Error -> {
                    binding.progressBar.hide()
                    Log.e("PertanyaanFragment","error get data")
                }
                is ResponseState.Loading -> {
                    binding.progressBar.show()
                }
                is ResponseState.Success -> {
                    binding.progressBar.hide()
                    list = response.data as ArrayList<Gejala>
                    showQuestion(counter)
                }
            }
        }
    }

    private fun showQuestion(idx: Int) {
        if (idx >= list.size){
            val namaKucing = arguments?.getString("nama_kucing")
            val gejalaTerpilih = hasil.filter { it.cf_user != 0.0 }
            for (i in gejalaTerpilih){
                listGejala.add(i.id_gejala.toString())
            }
            Log.d("PertanyaanFragment-Hasil", gejalaTerpilih.toString())
            Log.d("PertanyaanFragment-GejalaTerpilih", listGejala.toString())

            val bundle = Bundle()
            bundle.putString("nKucing", namaKucing)
            bundle.putParcelableArrayList("hasilUser", gejalaTerpilih as ArrayList<InputCfUser>)
            bundle.putStringArrayList("gejala", listGejala)

            toast("Mulai perhitungan")

            val intent = Intent(activity, HasilDiagnosisActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            requireActivity().finish()

        }else{
            val gejala = list[idx]
            binding.apply {
                textPertanyaan.text = gejala.pertanyaan
                rg.clearCheck()
            }

            if (idx == list.lastIndex){
                binding.layoutPertanyaan.visibility = View.GONE
                binding.btnLanjut.text = "Hasil diagnosis"
            }
        }
    }

    private fun getCfUserVal(answer: String): Double {
        var nCfUser = 0.0
        when(answer){
            "rb_1" -> nCfUser = 0.0 // Jika pilih tidak
            "rb_2" -> nCfUser = 0.2 // Jika pilih tidak tahu
            "rb_3"-> nCfUser = 0.4 // Jika pilih sedikit yakin
            "rb_4" -> nCfUser = 0.6 // Jika pilih cukup yakin
            "rb_5" -> nCfUser = 0.8 // Jika pilih yakin
            "rb_6" -> nCfUser = 1.0 // Jika pilih sangat yakin
        }
        return nCfUser;
    }

    private fun setQuestion(){
        if (binding.layoutPertanyaan.visibility == View.VISIBLE){
            val selected = binding.rg.checkedRadioButtonId
            if (selected == -1){
                toast("Pilih jawaban terlebih dahulu")
            }else{
                showQuestion(++counter)
                hasil.add(
                    InputCfUser(list[counter-1].id, getCfUserVal(resources.getResourceEntryName(selected)))
                )
            }
        }else{
            showQuestion(++counter)
            Log.d("Counter", "counter: $counter")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}