package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")
class SharedPref(private val context: Context) {

}