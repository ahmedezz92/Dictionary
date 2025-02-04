package com.example.dictionary.domain.model

data class Character(
    val id: Int,
    val name: String?,
    val title:String?,
    val description: String,
    val modified: String,
    val resourceURI: String,
)