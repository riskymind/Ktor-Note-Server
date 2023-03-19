package com.kaylayshi.data.model

@kotlinx.serialization.Serializable
data class Note(
    val id: String,
    val title: String,
    val content: String,
    val date: Long,
)
