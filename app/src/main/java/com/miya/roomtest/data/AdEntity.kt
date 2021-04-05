package com.miya.roomtest.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.miya.roomtest.Ad

fun covertAdToEntity(ad: Ad, programmeId: Int) = ad.let {
    AdEntity(
        it.adId,
        it.adName,
        it.adPageType,
        programmeId,
        it.aspectRatio,
        it.runningTime,
        it.showEndTime,
        it.showStartTime
    )
}

@Entity(
    tableName = "ad_table", foreignKeys = [
        ForeignKey(
            entity = ProgrammeEntity::class,
            parentColumns = ["programmeId"],
            childColumns = ["programmeOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ], indices = [Index("programmeOwnerId")]
)
data class AdEntity(
    @PrimaryKey val adId: Int,
    val adName: String,
    val adPageType: Int,
    val programmeOwnerId: Int,
    val aspectRatio: String,
    val runningTime: Int,
    val showEndTime: String,
    val showStartTime: String
)