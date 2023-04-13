package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentRegisterBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.viewmodel.AuthViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.isValidEmail
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import kotlinx.datetime.Clock
import java.security.Timestamp
import java.text.SimpleDateFormat
import java.util.*

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

    override fun onResume() {
        super.onResume()

        val gender = resources.getStringArray(R.array.jenis_kelamin)
        val arrayAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, gender)
        binding.spinnerJkReg.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            etTtlReg.setOnClickListener {
                getBornDate()
            }

            btnDaftar.setOnClickListener {
                registAccount()
            }

            btnToLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun getBornDate() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal lahir")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            binding.etTtlReg.setText(dateFormat.format(Date(it)))
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
                        gender = spinnerJkReg.text.toString(),
                        bornDate = etTtlReg.text.toString(),
                        imageUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                        password = etPasswordReg.text.toString(),
                        createAt = Clock.System.now().toString(),
                        updtaeAt = Clock.System.now().toString()
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
                    binding.progressBar.hide()
                    toast(response.msg)
                }
                is ResponseState.Loading -> {
                    binding.progressBar.show()
                }
                is ResponseState.Success -> {
                    binding.progressBar.hide()
                    toast(response.data)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    private fun validaton(): Boolean {
        var isValid = true

        binding.apply {
            if (etEmailReg.text.isNullOrEmpty()){
                isValid = false
                toast("Email tidak boleh kosong!")

                if (!etEmailReg.text.toString().isValidEmail()){
                    isValid = false
                    toast("Email tidak valid!")
                }
            }else if (etNameReg.text.isNullOrEmpty()){
                isValid = false
                toast("Nama lengkap tidak boleh kosong!")

            }else if (spinnerJkReg.text.isNullOrEmpty()){
                isValid = false
                toast("Jenis kelamin tidak boleh kosong, silahkan pilih!")

            }else if (etTtlReg.text.isNullOrEmpty()){
                isValid = false
                toast("Tanggal lahir tidak boleh kosong!")

            }else if (etPasswordReg.text.isNullOrEmpty() || etKonfirmPassReg.text.isNullOrEmpty()){
                isValid = false
                toast("Password tidak boleh kosong!")

                if (etPasswordReg.text.toString().length < 8){
                    isValid = false
                    toast("Password minimal 8 karakter!")

                    if (etKonfirmPassReg != etPasswordReg){
                        isValid = false
                        toast("Password tidak sama!")
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