package io.kamel.image.utils

import io.ktor.http.Url

internal val Url.path: String get() = encodedPath.removePrefix("/")