package io.dustin.domain.mugi.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.dustin.domain.mugi.model.code.ReleasedType
import io.dustin.domain.user.model.entity.User
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Table("mugi")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Mugi(
    @Id
    var id: Long? = null,

    @Column("user_id")
    var userId: Long,

    var name: String?,

    var label: String?,

    @Column("released_type")
    var releasedType: ReleasedType?,

    @Column("released_year")
    var releasedYear: Int?,

    var format: String?,

    @Column("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var createdAt: LocalDateTime? = now(),

    @Column("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var updatedAt: LocalDateTime? = null,
) {
    @Transient
    var user: User? = null
}