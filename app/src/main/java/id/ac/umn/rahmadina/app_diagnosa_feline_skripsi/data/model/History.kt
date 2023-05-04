package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History(
    var id : String = "",
    val idUser : String = "",
    val namaUser : String = "",
    val hdPenyakit : String = "",
    val hdCfPersen : String = "",
    val solusi : String? = ""
) : Parcelable
