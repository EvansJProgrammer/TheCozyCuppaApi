package dev.thecozycuppa.controller

import dev.thecozycuppa.dto.PageResponseDto
import dev.thecozycuppa.dto.PostDetailDto
import dev.thecozycuppa.entity.PostStatus
import dev.thecozycuppa.mapper.toDetailDto
import dev.thecozycuppa.mapper.toSummaryDto
import dev.thecozycuppa.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postRepository: PostRepository
) {

    @GetMapping
    fun getPublishedPosts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): PageResponseDto<dev.thecozycuppa.dto.PostSummaryDto> {

        val safeSize = size.coerceIn(1, 50)

        val pageable: Pageable = PageRequest.of(
            page.coerceAtLeast(0),
            safeSize,
            Sort.by(Sort.Direction.DESC, "publishedAt")
        )

        val result = postRepository.findAllByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED, pageable)

        return PageResponseDto(
            items = result.content.map { it.toSummaryDto() },
            page = result.number,
            size = result.size,
            totalItems = result.totalElements,
            totalPages = result.totalPages,
            hasNext = result.hasNext()
        )
    }


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
