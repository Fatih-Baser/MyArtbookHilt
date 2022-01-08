package com.fatihbaserpl.myartbookhilt.datamodel

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)