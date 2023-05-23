package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.diagnosis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R

@AndroidEntryPoint
class KonsultasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsultasi)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}