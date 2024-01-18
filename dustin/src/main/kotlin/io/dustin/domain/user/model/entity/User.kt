package io.dustin.domain.user.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.user.model.code.Job
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Table("User")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class User(
    @Id
    var id: Long? = null,
    var name: String,
    var job: Job?,
    @Column("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var createdAt: LocalDateTime? = now(),
    @Column("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var updatedAt: LocalDateTime? = null,
) {
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var mugis: List<Mugi>? = null
}