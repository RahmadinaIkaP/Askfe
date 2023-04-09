package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentRegisterBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.viewmodel.AuthViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val vmAuth : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnDaftar.setOnClickListener {
                registAccount()
                Toast.makeText(requireContext(), "Testing button register", Toast.LENGTH_SHORT).show()
            }

            btnToLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun registAccount() {
        binding.apply {
            if (validaton()){
                vmAuth.register(
                    etEmailReg.text.toString(),
                    etPasswordReg.text.toString(),
                    User(
                        id = "",
                        email = etEmailReg.text.toString(),
                        name = etNameReg.text.toString(),
                        gender = "",
                        bornDate = etTtlReg.text.toString(),
                        imageUrl = "",
                        password = etPasswordReg.text.toString()
                    )
                )

                observeRegister()
            }
        }
    }

    private fun observeRegister() {
        vmAuth.registerObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseState.Error -> {

                }
                is ResponseState.Loading -> {

                }
                is ResponseState.Success -> {

                }
            }
        }
    }

    private fun validaton(): Boolean {
        var isValid = true

        binding.apply {
            if (etEmailReg.text.isNullOrEmpty()){
                isValid = false
            }

            if (etNameReg.text.isNullOrEmpty()){
                isValid = false
            }

            if (spinnerJkReg.text.isNullOrEmpty()){
                isValid = false
            }

            if (etTtlReg.text.isNullOrEmpty()){
                isValid = false
            }

            if (etPasswordReg.text.isNullOrEmpty() || etKonfirmPassReg.text.isNullOrEmpty()){
                isValid = false
            }else{
                if (etPasswordReg.text.toString().length < 8){
                    isValid = false

                    if (etKonfirmPassReg != etPasswordReg){
                        isValid = false
                    }
                }
            }
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}