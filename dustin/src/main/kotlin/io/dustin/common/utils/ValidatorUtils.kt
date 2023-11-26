package io.dustin.common.utils

import io.dustin.common.exception.BadParameterException
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation

fun <T> validate(any: T) {
    val violations: Set<ConstraintViolation<T>> = Validation.buildDefaultValidatorFactory().validator.validate(any)
    if (violations.isNotEmpty()) {
        throw BadParameterException(violations.joinToString(separator = ", ") { "[${it.message}]" })
    }
}