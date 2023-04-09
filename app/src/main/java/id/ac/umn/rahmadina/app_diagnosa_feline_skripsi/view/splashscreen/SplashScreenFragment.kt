package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentSplashScreenBinding

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private var _binding : FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref : SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        sharedPref = SharedPref(requireContext())
        
        startSplashScreen()
    }

    private fun startSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}