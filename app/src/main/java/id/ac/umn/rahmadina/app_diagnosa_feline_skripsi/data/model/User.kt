package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class User(
    var id : String = "",
    var email : String = "",
    var name : String = "",
    var gender : String = "",
    var bornDate : String = "",
    var imageUrl : String? = "",
    var password : String = "",
    @ServerTimestamp
    var createAt : Date? = null,
    @ServerTimestamp
    var updtaeAt : Date? = null
) : Parcelable
