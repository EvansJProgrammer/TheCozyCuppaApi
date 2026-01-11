package dev.thecozycuppa.repository

import dev.thecozycuppa.entity.PostEntity
import dev.thecozycuppa.entity.PostStatus
import dev.thecozycuppa.entity.PostType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PostRepository : JpaRepository<PostEntity, UUID> {

    fun findBySlug(slug: String): PostEntity?

    fun existsBySlug(slug: String): Boolean

    fun findAllByStatusOrderByPublishedAtDesc(status: PostStatus): List<PostEntity>

    fun findAllByTypeAndStatusOrderByPublishedAtDesc(type: PostType, status: PostStatus): List<PostEntity>

    @Query(
        """
    select p from PostEntity p
    left join fetch p.tags
    where p.slug = :slug
    """
    )
    fun findBySlugWithTags(@Param("slug") slug: String): PostEntity?

    fun findAllByStatusAndTags_NameOrderByPublishedAtDesc(
        status: PostStatus,
        name: String
    ): List<PostEntity>
}
