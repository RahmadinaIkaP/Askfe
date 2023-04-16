package id.ac.umn.rahmadina.app_diagnosa_feline_skripsi.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")
class SharedPref(private val context: Context) {

    private val isLogin = booleanPreferencesKey("isLogin")
    private val uid = stringPreferencesKey("uid")
    private val name = stringPreferencesKey("name")
    private val email = stringPreferencesKey("email")

    suspend fun session(login : Boolean, id : String, names : String, emails : String){
        context.dataStore.edit {
            it[isLogin] = login
            it[uid] = id
            it[name] = names
            it[email] = emails
        }
    }

    val getSession : Flow<Boolean> = context.dataStore.data
        .map {
            it[isLogin] ?: false
        }

    val getUid : Flow<String> = context.dataStore.data
        .map {
            it[uid] ?: "undefined"
        }

    val getName : Flow<String> = context.dataStore.data
        .map {
            it[name] ?: "undefined"
        }

    val getEmail : Flow<String> = context.dataStore.data
        .map {
            it[email] ?: "undefined"
        }

    suspend fun removeSession(){
        context.dataStore.edit {
            it.clear()
        }
    }
}