package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val author: String,
    val platform: String,
    val url: String,
    val format: String,
    val fileType: String, // "VIDEO", "AUDIO", "THUMBNAIL"
    val fileSizeMB: Double,
    val durationStr: String,
    val thumbnailUrl: String,
    val filePath: String,
    val downloadedAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
