/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ksp.symbol

/**
 *  Classes, functions, properties and typealiases can be declared as `expect` in common modules and `actual` in platform modules.
 *
 *  See https://kotlinlang.org/docs/reference/platform-specific-declarations.html for more information.
 */
interface KSExpectActual {
    /**
     * True if this is an `actual` implementation.
     */
    val isActual: Boolean

    /**
     * True if this is an `expect` declaration.
     */
    val isExpect: Boolean

    /**
     * Finds all corresponding `actual` implementations for `this`.
     *
     * @return list of corresponding `actual` implementations.
     * @throws IllegalStateException if this is not an Kotlin `expect` class, function or property.
     */
    fun findActuals(): List<KSDeclaration>

    /**
     * Finds all corresponding `expect` declarations for `this`.
     *
     * @return list of corresponding `expect` declarations.
     * @throws IllegalStateException if this is not an Kotlin `actual` class, function, property or typealias.
     */
    fun findExpects(): List<KSDeclaration>
}