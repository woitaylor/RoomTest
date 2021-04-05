/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.miya.roomtest.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.miya.roomtest.data.AdSourceDao
import com.miya.roomtest.data.AppDatabase
import com.miya.roomtest.data.ProgrammeDao
import com.miya.roomtest.data.AdDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideAdDao(appDatabase: AppDatabase): AdDao {
        return appDatabase.adDao()
    }

    @Provides
    @Singleton
    fun provideAdSourceDao(appDatabase: AppDatabase): AdSourceDao {
        return appDatabase.adSourceDao()
    }

    @Provides
    @Singleton
    fun provideProgrammeDao(appDatabase: AppDatabase) : ProgrammeDao = appDatabase.programmeDao()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setPrettyPrinting().create()
}
