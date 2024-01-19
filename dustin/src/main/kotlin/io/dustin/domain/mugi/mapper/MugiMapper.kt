package io.dustin.domain.mugi.mapper

import io.dustin.domain.mugi.model.entity.Mugi
import io.dustin.domain.mugi.model.code.ReleasedType
import io.dustin.domain.user.model.entity.User
import io.dustin.domain.user.model.code.Job
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.function.BiFunction

@Component
class MugiMapper: BiFunction<Row, RowMetadata, Mugi> {
    override fun apply(row: Row, rowMetadata: RowMetadata): Mugi {
        val user = User(
            name = row.get("userName", String::class.java)!!,
            job = Job.valueOf(row.get("job", String::class.java)!!),
            createdAt = row.get("mCreatedAt", LocalDateTime::class.java),
            updatedAt = row.get("mUpdatedAt", LocalDateTime::class.java),
        )

        val mugi = Mugi(
            id = row.get("id", Long::class.javaObjectType)!!,
            userId = row.get("user_id", Long::class.javaObjectType)!!,
            name = row.get("name", String::class.java),
            label = row.get("label", String::class.java),
            releasedType = ReleasedType.valueOf(row.get("released_type", String::class.java)!!),
            releasedYear = row.get("released_year", Int::class.javaObjectType)!!,
            format = row.get("format", String::class.java),
            createdAt = row.get("created_at", LocalDateTime::class.java),
            updatedAt = row.get("updated_at", LocalDateTime::class.java),
        )
        mugi.user = user
        return mugi
    }
}
