package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile

import android.os.Bundle
import android.util.Log
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
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentUbahPasswordBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UbahPasswordFragment : Fragment() {
    private var _binding: FragmentUbahPasswordBinding? = null
    private val binding get() = _binding!!
    private val vmProfile : ProfileViewModel by viewModels()
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUbahPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.apply {

            imageView2.setOnClickListener {
                findNavController().navigateUp()
            }

            setTextPass()

            btnUbahPassword.setOnClickListener {
                if (validation()){
                    ubahPass()
                }
            }
        }
    }

    private fun ubahPass() {
        vmProfile.ubahPassword(binding.etUbahPass.text.toString())
        vmProfile.ubahPassObserver().observe(viewLifecycleOwner){
            when(it){
                is ResponseState.Error -> {
                    binding.progressBar.hide()
                    toast(it.msg)
                }
                is ResponseState.Loading -> {
                    binding.progressBar.show()
                }
                is ResponseState.Success -> {
                    binding.progressBar.hide()
                    updatePassDatabase(binding.etUbahPass.text.toString())
                    toast(it.data)
                }
            }
        }
    }

    private fun updatePassDatabase(pass : String) {
        vmProfile.updatePassDatabase(pass)
        vmProfile.updatePassObserver().observe(viewLifecycleOwner){
            when(it){
                is ResponseState.Error -> {
                    Log.e("UbahPasswordFragment","failed update data")
                }
                is ResponseState.Loading -> {
                    Log.d("UbahPasswordFragment","loading...")
                }
                is ResponseState.Success -> {
                    saveUpdatePass(pass)
                    Log.d("UbahPasswordFragment", it.data)
                }
            }
        }
    }

    private fun saveUpdatePass(password : String) {
        CoroutineScope(Dispatchers.IO).launch{
            sharedPref.updatedPass(password)
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        binding.apply {
            if (etUbahPass.text.toString() != etUbahKonfirmPass.text.toString()){
                isValid = false
                toast("Password tidak sama!")
            }
        }
        return  isValid
    }

    private fun setTextPass() {
        sharedPref.getPassword.asLiveData().observe(viewLifecycleOwner){
            if (it != "undefined"){
                binding.etUbahPass.setText(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}