package com.miya.roomtest.data

import androidx.room.Embedded
import androidx.room.Relation

data class AdAndAdSource(
    @Embedded
    val ad: AdEntity,

    @Relation(parentColumn = "adId", entityColumn = "adOwnerId")
    val sourceEntities: List<AdSourceEntity>
)
