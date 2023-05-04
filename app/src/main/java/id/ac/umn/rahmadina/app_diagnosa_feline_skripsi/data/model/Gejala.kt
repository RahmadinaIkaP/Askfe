package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gejala(
    val id : String? = "",
    val nama_gejala : String? = "",
    val pertanyaan : String? = ""
) : Parcelable
