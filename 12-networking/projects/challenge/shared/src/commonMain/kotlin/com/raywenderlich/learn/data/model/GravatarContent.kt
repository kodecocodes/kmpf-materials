package com.raywenderlich.learn.data.model

import kotlinx.serialization.Serializable

@Serializable
public data class GravatarProfile(
  val entry: List<GravatarEntry> = emptyList()
)

@Serializable
public data class GravatarEntry(
  val id: String? = null,
  val hash: String? = null,
  val preferredUsername: String? = null,
  val thumbnailUrl: String? = null,
  val aboutMe: String? = null
)