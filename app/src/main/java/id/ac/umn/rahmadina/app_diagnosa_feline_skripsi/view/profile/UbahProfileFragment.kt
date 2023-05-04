package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.User
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentUbahProfileBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.UriToFile
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.viewmodel.AuthViewModel
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.profile.viewmodel.ProfileViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UbahProfileFragment : Fragment() {
    private var _binding : FragmentUbahProfileBinding? = null
    private val binding get() = _binding!!
    private val vmProfile : ProfileViewModel by viewModels()
    private val vmAuth : AuthViewModel by viewModels()
    private var imgUri : Uri? = null

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 101

        const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"

        const val MAX_NUMBER_REQUEST_PERMISSIONS = 2
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var getFile: File? = null
    private val imagePic =
        registerForActivityResult(ActivityResultContracts.GetContent()){ uri : Uri? ->
            if (uri != null){
                val img = UriToFile(requireContext()).getImageBody(uri)
                getFile = img
                imgUri = uri
                binding.imgProfile.setImageURI(uri)
            }
        }

    private var permissionRequestCount : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUbahProfileBinding.inflate(inflater, container, false)

        requestPermissionsIfNecessary()

        savedInstanceState?.let {
            permissionRequestCount = it.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0)
        }

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

            getProfile()

            btnUbahProfile.setOnClickListener {
                editProfile(
                    etEmailProfile.text.toString(),
                    etNameProfile.text.toString(),
                    etTtlProfile.text.toString(),
                    spinnerJkProfile.text.toString(),
                    imgUri,
                    getFile
                )
            }
        }
    }

    private fun editProfile(email: String, name: String, ttl: String, gender: String, img: Uri?, file: File?) {
        if (img != null){
            img.let {
                file?.let { it1 ->
                    vmProfile.uploadImgProfile(it, it1){ response ->
                        when(response){
                            is ResponseState.Error -> {
                                Log.e("UbahProfileFragment","error get data")
                            }
                            is ResponseState.Loading -> {
                                Log.d("UbahProfileFragment", "loading...")
                            }
                            is ResponseState.Success -> {
                                updateUserDatabase(email, name, ttl, gender, response.data.toString())
                            }
                        }
                    }
                }
            }
        }else{
            vmAuth.getUser()
            vmAuth.getUserObserver().observe(viewLifecycleOwner){ response ->
                when(response){
                    is ResponseState.Error -> {
                        Log.e("UbahProfileFragment","error get data")
                    }
                    is ResponseState.Loading -> {
                        Log.d("UbahProfileFragment", "loading...")
                    }
                    is ResponseState.Success -> {
                        response.data[0].imageUrl?.let {
                            updateUserDatabase(email, name, ttl, gender, it)
                        }
                    }
                }
            }
        }
    }

    private fun updateUserDatabase(email: String, name: String, ttl: String, gender: String, img: String) {
        vmProfile.updateProfile(email,name,gender,ttl, img)
        vmProfile.updateProfileObserver().observe(viewLifecycleOwner){
            when (it){
                is ResponseState.Error -> {
                    binding.progressBar.hide()
                    toast("Ubah profile gagal..")
                }
                is ResponseState.Loading -> {
                    binding.progressBar.show()
                }
                is ResponseState.Success -> {
                    binding.progressBar.hide()
                    toast("Ubah profile berhasil!")
                }
            }
        }
    }

    private fun getProfile() {
        vmAuth.getUser()
        vmAuth.getUserObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseState.Error -> {
                    Log.e("UbahProfileFragment","error get data")
                }
                is ResponseState.Loading -> {
                    Log.d("UbahProfileFragment", "loading...")
                }
                is ResponseState.Success -> {
                    setUserVal(response)
                }
            }
        }
    }

    private fun setUserVal(response: ResponseState.Success<List<User>>) {
        binding.apply {
            Glide.with(requireView())
                .load(response.data[0].imageUrl)
                .into(imgProfile)

            etEmailProfile.setText(response.data[0].email)
            etNameProfile.setText(response.data[0].name)
            etTtlProfile.setText(response.data[0].bornDate)
            spinnerJkProfile.setText(response.data[0].gender)
        }
    }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        imagePic.launch("image/*")
    }

    private fun requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (permissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                // When the number of permission request retried is less than the max limit set
                permissionRequestCount += 1 // Increment the number of permission requests done
                // Request the required permissions for external storage access
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    permissions,
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                binding.btnEditImg.isEnabled = false
                // When the number of permission request retried exceeds the max limit set
                // Show a toast about how to update the permission for storage access
                Toast.makeText(
                    context,
                    "Go to Settings -> Apps and Notifications -> Flight Go Admin -> App Permissions and grant access to Storage.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkAllPermissions(): Boolean {
        // Boolean state to indicate all permissions are granted
        var hasPermissions = true
        // Verify all permissions are granted
        for (permission in permissions) {
            hasPermissions = hasPermissions && isPermissionGranted(permission)
        }
        // Return the state of all permissions granted
        return hasPermissions
    }

    private fun isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(requireContext(), permission) ==
            PackageManager.PERMISSION_GRANTED

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