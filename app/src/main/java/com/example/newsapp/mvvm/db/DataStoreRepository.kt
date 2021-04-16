package com.example.newsapp.mvvm.db

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_CATEGORY
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_CHIP_ID
import com.example.newsapp.mvvm.utility.Constants.Companion.DEFAULT_NEWS_COUNTRY
import com.example.newsapp.mvvm.utility.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.newsapp.mvvm.utility.Constants.Companion.PREFERENCES_NAME
import com.example.newsapp.mvvm.utility.Constants.Companion.PREFERENCES_NEWS_CATEGORY
import com.example.newsapp.mvvm.utility.Constants.Companion.PREFERENCES_NEWS_CATEGORY_ID
import com.example.newsapp.mvvm.utility.Constants.Companion.PREFERENCES_NEWS_COUNTRY
import com.example.newsapp.mvvm.utility.Constants.Companion.PREFERENCES_NEWS_COUNTRY_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context){


    private object PreferenceKeys{

        val selectedNewsCategory = stringPreferencesKey(PREFERENCES_NEWS_CATEGORY)
        val selectedNewsCategoryId = intPreferencesKey(PREFERENCES_NEWS_CATEGORY_ID)
        val selectedNewsCountry = stringPreferencesKey(PREFERENCES_NEWS_COUNTRY)
        val selectedNewsCountryId = intPreferencesKey(PREFERENCES_NEWS_COUNTRY_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveNewsTipsType(newsCategory: String,newsCategoryId: Int,newsCountry: String,newsCountryId: Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedNewsCategory] = newsCategory
            preferences[PreferenceKeys.selectedNewsCategoryId] = newsCategoryId
            preferences[PreferenceKeys.selectedNewsCountry] = newsCountry
            preferences[PreferenceKeys.selectedNewsCountryId] = newsCountryId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean){
        dataStore.edit { preferences->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readNewsTipsType: Flow<NewsTipsType> = dataStore.data
            .catch { exception->
                if (exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map { preferences ->
                val selectedNewsCategory = preferences[PreferenceKeys.selectedNewsCategory] ?: DEFAULT_NEWS_CATEGORY
                val selectedNewsCategoryId = preferences[PreferenceKeys.selectedNewsCategoryId] ?: DEFAULT_NEWS_CHIP_ID
                val selectedNewsCountry = preferences[PreferenceKeys.selectedNewsCountry] ?: DEFAULT_NEWS_COUNTRY
                val selectedNewsCountryId = preferences[PreferenceKeys.selectedNewsCountryId] ?: DEFAULT_NEWS_CHIP_ID
                NewsTipsType(
                        selectedNewsCategory,
                        selectedNewsCategoryId,
                        selectedNewsCountry,
                        selectedNewsCountryId
                )
            }

    val readBackOnline: Flow<Boolean> = dataStore.data
            .catch { exception->
                if (exception is IOException){
                    emit(emptyPreferences())
                } else{
                    throw exception
                }
            }
            .map { preferences->
                val backOnline = preferences[PreferenceKeys.backOnline] ?: false
                backOnline //this means return value!!
            }
}

data class NewsTipsType(
        val selectedNewsCategory: String,
        val selectedNewsCategoryId: Int,
        val selectedNewsCountry: String,
        val selectedNewsCountryId: Int
)