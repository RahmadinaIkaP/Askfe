package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentFormCatNameBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast

@AndroidEntryPoint
class FormCatNameFragment : Fragment() {
    private var _binding: FragmentFormCatNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormCatNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnNextToTheQuestion.setOnClickListener {
                if (etNamaKucing.text.toString().isNullOrEmpty()){
                    toast("Silahkan isi nama kucing anda")
                }else{
                    val bundle = Bundle()
                    bundle.putString("nama_kucing", etNamaKucing.text.toString())
                    findNavController().navigate(R.id.action_formCatNameFragment_to_pertanyaanFragment, bundle)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}