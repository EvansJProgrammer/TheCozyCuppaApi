package dev.thecozycuppa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(
    name = "posts",
    indexes = [
        Index(name = "idx_posts_type", columnList = "type"),
        Index(name = "idx_posts_status", columnList = "status"),
        Index(name = "idx_posts_published_at", columnList = "published_at"),
    ]
)
class PostEntity(

    @Id
    @Column(name = "id", nullable = false)
    val id: UUID? = null,

    @Column(name = "slug", nullable = false, unique = true)
    var slug: String,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "summary")
    var summary: String? = null,

    @Column(name = "content", nullable = false, columnDefinition = "text")
    var content: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: PostType,

    @Column(name = "cover_image_url")
    var coverImageUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: PostStatus = PostStatus.DRAFT,

    @Column(name = "published_at")
    var publishedAt: OffsetDateTime? = null,

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    val createdAt: OffsetDateTime? = null,

    @Column(name = "updated_at", nullable = false, insertable = false)
    val updatedAt: OffsetDateTime? = null,

    @field:ManyToMany(fetch = FetchType.LAZY)
    @field:JoinTable(
        name = "post_tags",
        schema = "public",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )

    val tags: MutableSet<TagEntity> = linkedSetOf()



) {
    protected constructor() : this(
        slug = "",
        title = "",
        content = "",
        type = PostType.ESSAY
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostEntity) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
