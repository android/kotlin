/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ksp.symbol.impl.java

import org.jetbrains.kotlin.ksp.symbol.KSAnnotation
import org.jetbrains.kotlin.ksp.symbol.KSName
import org.jetbrains.kotlin.ksp.symbol.Origin
import org.jetbrains.kotlin.ksp.symbol.impl.kotlin.KSValueArgumentImpl

class KSValueArgumentJavaImpl(override val name: KSName?, override val value: Any?) : KSValueArgumentImpl() {
    companion object {
        private val cache = mutableMapOf<Pair<KSName?, Any?>, KSValueArgumentJavaImpl>()

        fun getCached(name: KSName?, value: Any?) = cache.getOrPut(Pair(name, value)) { KSValueArgumentJavaImpl(name, value) }
    }

    override val origin = Origin.JAVA

    override val isSpread: Boolean = false

    override val annotations: List<KSAnnotation> = emptyList()
}