package com.miya.roomtest.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.miya.roomtest.Programme

fun covertProgrammeToEntity(programme: Programme): ProgrammeEntity =
    programme.let {
        ProgrammeEntity(
            it.programmeId,
            it.programmeName,
            it.showDate
        )
    }


@Entity(tableName = "programme", indices = [Index("programmeId")])
data class ProgrammeEntity(
    @PrimaryKey val programmeId: Int,
    val programmeName: String,
    val showDate: String
)