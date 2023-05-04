package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputCfUser(
    val id_gejala : String? = "",
//    val nama_gejala : String? = "",
    val cf_user : Double? = 0.0
) : Parcelable
