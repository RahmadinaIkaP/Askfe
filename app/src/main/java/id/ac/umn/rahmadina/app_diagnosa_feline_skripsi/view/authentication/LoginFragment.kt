package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentLoginBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val vmAuth : AuthViewModel by viewModels()
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.apply {
            btnToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                loginAccount()
            }
        }
    }

    private fun loginAccount() {
        binding.apply {
            if (validation()){
                vmAuth.login(etEmailLogin.text.toString(), etPasswordLogin.text.toString())

                observeLogin()
            }
        }
    }

    private fun observeLogin() {
        vmAuth.loginObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseState.Error -> {
                    binding.progressBar.hide()
                    toast(response.msg)
                }
                is ResponseState.Loading -> {
                    binding.progressBar.show()
                }
                is ResponseState.Success -> {
                    binding.progressBar.hide()
                    saveSession()
                    toast(response.data)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }

    private fun saveSession() {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.session(true)
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        binding.apply {
            if (etEmailLogin.text.isNullOrEmpty()){
                isValid = false
                toast("Email kosong, silahkan isi terlebih dahulu")
            }else if (etPasswordLogin.text.isNullOrEmpty()){
                isValid = false
                toast("Password kosong, silahkan isi terlebih dahulu")
            }
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}