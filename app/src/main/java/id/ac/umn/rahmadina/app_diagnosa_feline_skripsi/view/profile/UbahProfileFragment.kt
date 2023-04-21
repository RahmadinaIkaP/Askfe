package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentUbahProfileBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UbahProfileFragment : Fragment() {
    private var _binding : FragmentUbahProfileBinding? = null
    private val binding get() = _binding!!
    private val vmProfile : ProfileViewModel by viewModels()

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 101

        const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"

        const val MAX_NUMBER_REQUEST_PERMISSIONS = 2
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUbahProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val gender = resources.getStringArray(R.array.jenis_kelamin)
        val arrayAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, gender)
        binding.spinnerJkProfile.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnEditImg.setOnClickListener {
                openGallery()
            }

            etTtlProfile.setOnClickListener {
                getBornDate()
            }

            btnUbahProfile.setOnClickListener {

            }
        }
    }

    private fun openGallery() {

    }

    private fun getBornDate() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal lahir")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            binding.etTtlProfile.setText(dateFormat.format(Date(it)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}