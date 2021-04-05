/*
 * Copyright 2018 Google LLC
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

package com.miya.roomtest.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [GardenPlanting] class.
 */
@Dao
interface AdDao {
    @Query("SELECT * FROM ad_table")
    fun getAd(): Flow<List<AdEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM ad_source_table WHERE adCss = :adId LIMIT 1)")
    fun isAdSourceExists(adId: String): Flow<Boolean>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
//    @Transaction
//    @Query("SELECT * FROM ad_table WHERE adId IN (SELECT DISTINCT(adOwnerId) FROM ad_source_table)")
//    fun getAdAnAdSources(): Flow<List<AdAndAdSource>>

    @Transaction
    @Query("SELECT * FROM ad_table")
    fun getAdAnAdSources(): Flow<List<AdAndAdSource>>

    @Transaction
    @Query("SELECT * FROM ad_table WHERE adId IN (:adIds)")
    fun getAdAnAdSourcesById(adIds: List<Int>): Flow<List<AdAndAdSource>>

    @Transaction
    @Query("SELECT * FROM ad_table WHERE adId =:adId")
    fun getAdAnAdSourcesById(adId: Int): Flow<AdAndAdSource>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAd(ad: AdEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAd(ads: List<AdEntity>)

    @Delete
    suspend fun deleteAd(ad: AdEntity)
}
