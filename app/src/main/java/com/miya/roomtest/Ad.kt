package com.miya.roomtest

data class Ad(
    val adId: Int,
    val adName: String,
    val adPageType: Int,
    val adSourceList: List<AdSource>,
    val aspectRatio: String,
    val runningTime: Int,
    val showEndTime: String,
    val showStartTime: String
)