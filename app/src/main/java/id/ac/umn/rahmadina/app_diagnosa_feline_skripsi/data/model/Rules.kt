package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rules(
    val id : Int? = 0,
    val id_gejala : String? = "",
    val id_penyakit : String? = "",
    val nilai_cf : Double = 0.0
) : Parcelable
