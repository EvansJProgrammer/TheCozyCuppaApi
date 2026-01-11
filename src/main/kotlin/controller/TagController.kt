package dev.thecozycuppa.controller

import dev.thecozycuppa.dto.PostSummaryDto
import dev.thecozycuppa.dto.TagDto
import dev.thecozycuppa.entity.PostStatus
import dev.thecozycuppa.mapper.toDto
import dev.thecozycuppa.mapper.toSummaryDto
import dev.thecozycuppa.repository.PostRepository
import dev.thecozycuppa.repository.TagRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tags")
class TagController(
    private val tagRepository: TagRepository,
    private val postRepository: PostRepository
) {

    @GetMapping
    fun listTags(): List<TagDto> =
        tagRepository.findAll()
            .sortedBy { it.name.lowercase() }
            .map { it.toDto() }

    @GetMapping("/{name}/posts")
    fun getPublishedPostsByTag(@PathVariable name: String): ResponseEntity<List<PostSummaryDto>> {
        val tagExists = tagRepository.existsByName(name)
        if (!tagExists) return ResponseEntity.notFound().build()

        val posts = postRepository
            .findAllByStatusAndTags_NameOrderByPublishedAtDesc(PostStatus.PUBLISHED, name)
            .map { it.toSummaryDto() }

        return ResponseEntity.ok(posts)
    }
}
