package dev.thecozycuppa.dto

import dev.thecozycuppa.entity.PostType
import java.time.OffsetDateTime

data class PostDetailDto(
    val slug: String,
    val title: String,
    val summary: String?,
    val content: String,
    val type: PostType,
    val coverImageUrl: String?,
    val publishedAt: OffsetDateTime?,
    val tags: List<String>
)
