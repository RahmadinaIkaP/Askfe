package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model.History
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentHistoryBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.ResponseState
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util.toast
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis.viewmodel.DiagnosisViewModel

@AndroidEntryPoint
class HistoryFragment : Fragment(), HistoryAdapter.HistoryInterface {
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private val vmHistory : DiagnosisViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        getHistoryDiagnose()
    }

    private fun getHistoryDiagnose() {
        sharedPref.getUid.asLiveData().observe(viewLifecycleOwner){ id ->
            vmHistory.getHistoryUser(id)
        }
        vmHistory.getHistoryObserver().observe(viewLifecycleOwner){
            when(it){
                is ResponseState.Error -> {
                    binding.progressBar.hide()
                }
                is ResponseState.Loading -> {
                    binding.progressBar.show()
                }
                is ResponseState.Success -> {
                    binding.progressBar.hide()
                    populateData(it.data)
                }
            }
        }
    }

    private fun populateData(data: List<History>) {
        adapter = HistoryAdapter(this)
        adapter.setData(data)

        binding.apply {
            rvHistoryDiagnose.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvHistoryDiagnose.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(history: History) {
        toast("bisa diklik")
    }
}