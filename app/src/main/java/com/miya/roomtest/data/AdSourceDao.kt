package com.miya.roomtest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AdSourceDao {

    @Query("SELECT * FROM ad_source_table")
    fun getAdSource(): Flow<List<AdSourceEntity>>

    @Query("SELECT * FROM ad_source_table WHERE adOwnerId = :adId")
    fun getAdSourceById(adId: Int): Flow<List<AdSourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAdSource(adSourceEntities:List<AdSourceEntity>)
}