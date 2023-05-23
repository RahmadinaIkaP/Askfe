package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.history

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.History
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentDetailHistoryBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentDetailInfoPenyakitBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailHistoryFragment : Fragment() {
    private var _binding : FragmentDetailHistoryBinding? = null
    private val binding get() = _binding!!
    private val args : DetailHistoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val data = args.dataHistory

        setDataHistory(data)
    }

    @SuppressLint("SetTextI18n")
    private fun setDataHistory(data: History) {
        binding.apply {
            val konsulDate = SimpleDateFormat("d MMMM y",
                Locale.getDefault()).format(data.tglKonsultasi!!)
            val builder = StringBuilder()

            tvTanggalKonsultasi.text = konsulDate
            tvNamaKucing.text = data.namaKucing
            tvHasilDiagnosa.text = data.hdPenyakit
            tvNilaiPersen.text = "${data.hdCfPersen}%"
            tvPenjelasan.text = data.hdDeskripsi

            for (solusi in data.hdSolusi){
                builder.append(solusi).append("\n")
            }
            tvSolusi.text = builder.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}