package dev.thecozycuppa.mapper

import dev.thecozycuppa.dto.TagDto
import dev.thecozycuppa.entity.TagEntity

fun TagEntity.toDto(): TagDto =
    TagDto(
        id = requireNotNull(id) { "TagEntity.id should not be null after persistence" },
        name = name
    )
