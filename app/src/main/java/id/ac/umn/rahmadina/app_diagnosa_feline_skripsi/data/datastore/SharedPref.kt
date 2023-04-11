package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")
class SharedPref(private val context: Context) {

    private val isLogin = booleanPreferencesKey("isLogin")

    suspend fun session(login : Boolean){
        context.dataStore.edit {
            it[isLogin] = login
        }
    }

    val getSession : Flow<Boolean> = context.dataStore.data
        .map {
            it[isLogin] ?: false
        }

    suspend fun removeSession(){
        context.dataStore.edit {
            it.clear()
        }
    }
}