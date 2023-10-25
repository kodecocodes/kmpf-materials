package com.chromaticnoise.multiplatformswiftpackage.domain

internal class XCFrameworkName private constructor(val value: String) {

    internal companion object {
        fun of(name: String?): Either<PluginConfiguration.PluginConfigurationError, XCFrameworkName> =
                name?.ifNotBlank { Either.Right(XCFrameworkName(it)) }
                        ?: Either.Left(PluginConfiguration.PluginConfigurationError.BlankXCFrameworkName)
    }

    override fun equals(other: Any?): Boolean = value == (other as? XCFrameworkName)?.value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = "XCFrameworkName(value='$value')"
}