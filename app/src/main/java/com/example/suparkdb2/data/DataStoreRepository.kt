package com.example.suparkdb2.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("login_session")

// May use savedStateHandle for first version
@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val dataStore = context.dataStore

    private object PreferenceKeys {
        val nameKey = stringPreferencesKey("user_name")
        val surnameKey = stringPreferencesKey("user_surname")
        val suIdKey = intPreferencesKey("user_su_id")
    }

    suspend fun persistSuId(suId: Int){
        dataStore.edit {
            it[PreferenceKeys.suIdKey] = suId
        }
    }

    val suIdKeyFlow = dataStore.data.map {
        it[PreferenceKeys.suIdKey] ?: 0
    }

    suspend fun clearAllData(){
       dataStore.edit {
           it.clear()
       }
    }

}