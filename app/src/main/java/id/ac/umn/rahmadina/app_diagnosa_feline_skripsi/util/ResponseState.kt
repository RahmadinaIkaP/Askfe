package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.util

sealed class ResponseState<out T> {
    class Success<out T>(val data: T) : ResponseState<T>()
    class Error(val msg: String) : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
}