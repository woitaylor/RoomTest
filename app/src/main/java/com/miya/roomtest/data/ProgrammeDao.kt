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
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface ProgrammeDao {
    @Query("SELECT * FROM programme ORDER BY programmeName")
    fun getProgramme(): Flow<List<ProgrammeEntity>>

    @Query("SELECT * FROM programme WHERE programmeId = :programmeId ORDER BY programmeName")
    fun getProgrammeById(programmeId: Int): Flow<List<ProgrammeEntity>>

    @Transaction
    @Query("SELECT * FROM programme ")
    fun getProgrammeAndAd(): Flow<List<ProgrammeAndAd>>

    @Transaction
    @Query("SELECT * FROM programme WHERE programmeId = :programmeOwnerId")
    fun getProgrammeAndAdById(programmeOwnerId: Int): Flow<ProgrammeAndAd>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<ProgrammeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgramme(programmeEntity: ProgrammeEntity)

    @RawQuery
    suspend fun deleteProgrammeById(supportSQLiteQuery: SupportSQLiteQuery): Long
}
