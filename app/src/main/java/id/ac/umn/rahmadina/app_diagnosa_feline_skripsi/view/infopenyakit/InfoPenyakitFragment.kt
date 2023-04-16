package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.infopenyakit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.Penyakit
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentInfoPenyakitBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.infopenyakit.viewmodel.DiseaseViewModel

@AndroidEntryPoint
class InfoPenyakitFragment : Fragment(), PenyakitAdapter.PenyakitInterface {
    private var _binding: FragmentInfoPenyakitBinding? = null
    private val binding get() = _binding!!
    private val vmDisease : DiseaseViewModel by viewModels()
    private lateinit var adapter: PenyakitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoPenyakitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmDisease.getDiseaseInfo()
        vmDisease.diseaseObserver().observe(viewLifecycleOwner){ response ->
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
                    showDisease(response.data)
                }
            }
        }
    }

    private fun showDisease(list: List<Penyakit>) {
        adapter = PenyakitAdapter(this)
        adapter.setData(list)

        binding.apply {
            rvPenyakit.layoutManager = GridLayoutManager(requireContext(), 2)
            rvPenyakit.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(penyakit: Penyakit) {
        TODO("Not yet implemented")
    }
}