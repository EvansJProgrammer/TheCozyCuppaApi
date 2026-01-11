package dev.thecozycuppa.mapper

import dev.thecozycuppa.dto.PostDetailDto
import dev.thecozycuppa.dto.PostSummaryDto
import dev.thecozycuppa.entity.PostEntity

fun PostEntity.toSummaryDto(): PostSummaryDto =
    PostSummaryDto(
        slug = slug,
        title = title,
        summary = summary,
        type = type,
        coverImageUrl = coverImageUrl,
        publishedAt = publishedAt,
        tags = tags.map { it.name }.sorted()
    )

fun PostEntity.toDetailDto(): PostDetailDto =
    PostDetailDto(
        slug = slug,
        title = title,
        summary = summary,
        content = content,
        type = type,
        coverImageUrl = coverImageUrl,
        publishedAt = publishedAt,
        tags = tags.map { it.name }.sorted()
    )
