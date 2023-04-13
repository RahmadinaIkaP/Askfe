package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id : String,
    var email : String,
    var name : String,
    var gender : String,
    var bornDate : String,
    var imageUrl : String? = null,
    var password : String,
    var createAt : String,
    var updtaeAt : String
) : Parcelable
