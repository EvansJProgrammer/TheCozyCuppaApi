package dev.thecozycuppa.controller

import dev.thecozycuppa.dto.PostDetailDto
import dev.thecozycuppa.dto.PostSummaryDto
import dev.thecozycuppa.entity.PostStatus
import dev.thecozycuppa.mapper.toDetailDto
import dev.thecozycuppa.mapper.toSummaryDto
import dev.thecozycuppa.repository.PostRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postRepository: PostRepository
) {

    @GetMapping
    fun getPublishedPosts(): List<PostSummaryDto> =
        postRepository
            .findAllByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED)
            .map { it.toSummaryDto() }

    @GetMapping("/{slug}")
    fun getPostBySlug(@PathVariable slug: String): ResponseEntity<PostDetailDto> {
        val post = postRepository.findBySlugWithTags(slug)
            ?: return ResponseEntity.notFound().build()

        if (post.status != PostStatus.PUBLISHED) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(post.toDetailDto())
    }
}
