package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class History(
    var id : String = "",
    val idUser : String? = "",
    val namaKucing : String? = "",
    val hdPenyakit : String? = "",
    val hdCfPersen : String? = "",
    val hdDeskripsi : String? = "",
    val hdSolusi : List<String?> = arrayListOf(),
    @ServerTimestamp
    val tglKonsultasi : Date? = null
) : Parcelable
