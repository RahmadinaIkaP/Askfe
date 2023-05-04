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
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentProfileBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private val vmAuth : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.apply {
            navigateToProfile.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_profileSayaFragment)
            }

            navigateToAboutApp.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_aboutAppFragment)
            }

            setProfile()

            btnLogout.setOnClickListener {
                clearSession()
                logout()
            }
        }
    }

    private fun setProfile() {
        vmAuth.getUser()
        vmAuth.getUserObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseState.Error -> {
                    Log.e("LoginFragment","error get data")}
                is ResponseState.Loading -> {
                    Log.d("LoginFragment", "loading...")}
                is ResponseState.Success -> {
                    Log.d("LoginFragment", response.data[0].toString())

                    binding.apply {
                        Glide.with(requireView())
                            .load(response.data[0].imageUrl)
                            .into(circleImageView2)

                        tvNamaUser.text = response.data[0].name
                        tvEmailUser.text = response.data[0].email
                    }
                }
            }
        }
    }

    private fun clearSession() {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.removeSession()
        }
    }

    private fun logout() {
        vmAuth.logout {
            toast("Logout berhasil!")
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}