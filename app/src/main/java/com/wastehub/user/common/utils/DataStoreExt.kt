package com.wastehub.user.common.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

const val SERIAL_DATA_STORE_NAME = "session"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SERIAL_DATA_STORE_NAME)