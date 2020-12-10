package com.example.wheretoeat.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.wheretoeat.util.Constants.Companion.PREFERENCE_BACK_ONLINE
import com.example.wheretoeat.util.Constants.Companion.PREFERENCE_NAME
import com.example.wheretoeat.util.Constants.Companion.PREFERENCE_PRICE_CATEGORY_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedPriceCategoryId = preferencesKey<Int>(PREFERENCE_PRICE_CATEGORY_ID)
        val backOnline = preferencesKey<Boolean>(PREFERENCE_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun savePriceCategory(
        priceCategoryId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedPriceCategoryId] = priceCategoryId
        }
    }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readPriceCategory: Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedPriceCategoryId = preferences[PreferenceKeys.selectedPriceCategoryId] ?: 0
            selectedPriceCategoryId
        }
}
