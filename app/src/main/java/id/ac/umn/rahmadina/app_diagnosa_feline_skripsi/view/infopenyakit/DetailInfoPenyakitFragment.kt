package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.infopenyakit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.FragmentDetailInfoPenyakitBinding

class DetailInfoPenyakitFragment : Fragment() {
    private var _binding : FragmentDetailInfoPenyakitBinding? = null
    private val binding get() = _binding!!
    private val args : DetailInfoPenyakitFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailInfoPenyakitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = args.argsPenyakit

        binding.apply {
            imageView2.setOnClickListener {
                findNavController().navigateUp()
            }

            tvNamaPenyakit.text = data.nama_penyakit
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            data.urlArtikel?.let { loadUrl(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}