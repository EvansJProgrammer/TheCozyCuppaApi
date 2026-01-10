package dev.thecozycuppa.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "tags")
class TagEntity(

    @Id
    @Column(name = "id", nullable = false)
    val id: UUID? = null,

    @Column(name = "name", nullable = false, unique = true)
    var name: String

) {
    protected constructor() : this(name = "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TagEntity) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
