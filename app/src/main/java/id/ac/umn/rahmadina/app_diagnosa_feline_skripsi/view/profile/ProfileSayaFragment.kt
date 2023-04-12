package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentProfileSayaBinding

@AndroidEntryPoint
class ProfileSayaFragment : Fragment() {
    private var _binding: FragmentProfileSayaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileSayaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            navigateToEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_profileSayaFragment_to_ubahProfileFragment)
            }

            navigateToPass.setOnClickListener {
                findNavController().navigate(R.id.action_profileSayaFragment_to_ubahPasswordFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}