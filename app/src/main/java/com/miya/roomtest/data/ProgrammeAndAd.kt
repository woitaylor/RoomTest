package com.miya.roomtest.data

import androidx.room.Embedded
import androidx.room.Relation

data class ProgrammeAndAd(
    @Embedded
    val programmeEntity: ProgrammeEntity,

    @Relation(parentColumn = "programmeId", entityColumn = "programmeOwnerId")
    val adEntities: List<AdEntity>
)
