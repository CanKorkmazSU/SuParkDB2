package com.example.suparkdb2.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("login_session")

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val dataStore = context.dataStore

    private object PreferenceKeys {
        val nameKey = stringPreferencesKey("user_name")
        val surnameKey = stringPreferencesKey("user_surname")
        val suIdKey = stringPreferencesKey("user_su_id")
    }
}