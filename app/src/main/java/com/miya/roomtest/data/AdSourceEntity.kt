package com.miya.roomtest.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.miya.roomtest.AdSource


fun covertAdSourceToEntity(adSource: AdSource, adId: Int) = adSource.let {
    AdSourceEntity(
        adId,
        it.adCss,
        it.adSourceInfo,
        it.adSourceSort,
        it.adSourceType,
        it.adVideoType,
        it.pageNum,
        it.sourceRunningTime,
        it.switchingEffects
    )
}

@Entity(
    tableName = "ad_source_table", primaryKeys = ["adSourceInfo", "adOwnerId"],
    foreignKeys = [
        ForeignKey(
            entity = AdEntity::class,
            parentColumns = ["adId"],
            childColumns = ["adOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("adOwnerId"), Index("adSourceInfo")]
)
data class AdSourceEntity(
    val adOwnerId: Int,
    val adCss: String,
    val adSourceInfo: String,
    val adSourceSort: Int,
    val adSourceType: Int,
    val adVideoType: Int,
    val pageNum: Int,
    val sourceRunningTime: Int,
    val switchingEffects: Int
)