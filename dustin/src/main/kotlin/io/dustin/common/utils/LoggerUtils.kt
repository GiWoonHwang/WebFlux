package io.dustin.common.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * reified 통해 T의 타입을 알 수 있다. -> getLogger (T::class.java) 을 받을 수 있게 된다.
 */
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)