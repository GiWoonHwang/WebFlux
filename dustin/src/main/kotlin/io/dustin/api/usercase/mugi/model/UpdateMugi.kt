package io.dustin.api.usercase.mugi.model

import io.dustin.domain.mugi.model.Mugi
import io.dustin.domain.mugi.model.code.ReleasedType
import org.springframework.data.relational.core.sql.SqlIdentifier

data class UpdateMugi(
    val name: String? = null,
    var label: String? = null,
    @field:EnumCheck(enumClazz = ReleasedType::class, permitNull = true, message = "releasedType 필드는 ADAE, GUM, JIPANGI, HWAL만  가능합니다.")
    var releasedType: String? = null,
    var releasedYear: Int? = null,
    var format: String? = null,
) {
    fun createAssignments(mugi: Mugi): Pair<Mugi, MutableMap<SqlIdentifier, Any>> {
        val assignments = mutableMapOf<SqlIdentifier, Any>()
        name?.let {
            assignments[SqlIdentifier.unquoted("name")] = it
            mugi.name = it
        }
        label?.let {
            assignments[SqlIdentifier.unquoted("label")] = it
            mugi.label = it
        }
        releasedType?.let {
            assignments[SqlIdentifier.unquoted("released_type")] = it
            mugi.releasedType = it
        }
        releasedYear?.let {
            assignments[SqlIdentifier.unquoted("released_year")] = it
            mugi.releasedYear = it
        }
        format?.let {
            assignments[SqlIdentifier.unquoted("format")] = it
            mugi.format = it
        }
        return mugi to assignments
    }
}