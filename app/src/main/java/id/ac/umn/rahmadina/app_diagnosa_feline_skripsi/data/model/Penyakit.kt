package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Penyakit(
    val id : String,
    val nama_penyakit : String,
    val deskripsi : String,
//    val solusi : String,
    val image : String
) : Parcelable
