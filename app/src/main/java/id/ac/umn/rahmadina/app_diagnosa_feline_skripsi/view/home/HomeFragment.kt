package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentHomeBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.viewmodel.AuthViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis.KonsultasiActivity

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private val vmAuth : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        setGreeting()

        binding.apply {
            btnMulaiDiagnosa.setOnClickListener {
                val intent = Intent(activity, KonsultasiActivity::class.java)
                startActivity(intent)
            }

            btnPenyakitFeline.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_infoPenyakitFragment)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setGreeting() {
        sharedPref.getName.asLiveData().observe(viewLifecycleOwner){ name ->
            binding.tvGreeting.text = "Halo ${name}, selamat datang di AskFe!"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}