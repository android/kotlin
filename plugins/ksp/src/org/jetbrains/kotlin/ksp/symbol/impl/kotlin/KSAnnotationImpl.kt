/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ksp.symbol.impl.kotlin

import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.ksp.processing.impl.ResolverImpl
import org.jetbrains.kotlin.ksp.symbol.*
import org.jetbrains.kotlin.ksp.symbol.impl.KSObjectCache
import org.jetbrains.kotlin.ksp.symbol.impl.binary.createKSValueArguments
import org.jetbrains.kotlin.ksp.symbol.impl.toLocation
import org.jetbrains.kotlin.psi.KtAnnotationEntry

class KSAnnotationImpl private constructor(val ktAnnotationEntry: KtAnnotationEntry) : KSAnnotation {
    companion object : KSObjectCache<KtAnnotationEntry, KSAnnotationImpl>() {
        fun getCached(ktAnnotationEntry: KtAnnotationEntry) = cache.getOrPut(ktAnnotationEntry) { KSAnnotationImpl(ktAnnotationEntry) }
    }

    override val origin = Origin.KOTLIN

    override val location: Location by lazy {
        ktAnnotationEntry.toLocation()
    }

    override val annotationType: KSTypeReference by lazy {
        KSTypeReferenceImpl.getCached(ktAnnotationEntry.typeReference!!)
    }

    override val arguments: List<KSValueArgument> by lazy {
        resolved?.createKSValueArguments() ?: listOf()
    }

    override val shortName: KSName by lazy {
        KSNameImpl.getCached(ktAnnotationEntry.shortName!!.asString())
    }

    override val useSiteTarget: AnnotationUseSiteTarget? by lazy {
        when (ktAnnotationEntry.useSiteTarget?.getAnnotationUseSiteTarget()) {
            null -> null
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.FILE -> AnnotationUseSiteTarget.FILE
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.PROPERTY -> AnnotationUseSiteTarget.PROPERTY
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.FIELD -> AnnotationUseSiteTarget.FIELD
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.PROPERTY_GETTER -> AnnotationUseSiteTarget.GET
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.PROPERTY_SETTER -> AnnotationUseSiteTarget.SET
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.RECEIVER -> AnnotationUseSiteTarget.RECEIVER
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER -> AnnotationUseSiteTarget.PARAM
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.SETTER_PARAMETER -> AnnotationUseSiteTarget.SETPARAM
            org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD -> AnnotationUseSiteTarget.DELEGATE
        }
    }

    override fun <D, R> accept(visitor: KSVisitor<D, R>, data: D): R {
        return visitor.visitAnnotation(this, data)
    }

    private val resolved: AnnotationDescriptor? by lazy {
        ResolverImpl.instance.resolveAnnotationEntry(ktAnnotationEntry)
    }
}
