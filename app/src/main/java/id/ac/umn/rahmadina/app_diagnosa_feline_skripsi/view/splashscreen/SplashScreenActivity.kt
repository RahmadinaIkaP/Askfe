package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.R
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore.SharedPref
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.databinding.ActivitySplashScreenBinding
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.MainActivity
import id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.view.authentication.AuthActivity

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var sharedPref : SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        startSplashScreen()
    }

    private fun startSplashScreen() {
        sharedPref.getSession.asLiveData().observe(this){
            Handler(Looper.getMainLooper()).postDelayed({
                if (it == false){
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 2000)
        }
    }
}