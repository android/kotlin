/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ksp.symbol.impl.java

import com.intellij.psi.PsiField
import com.intellij.psi.PsiJavaFile
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.ksp.isOpen
import org.jetbrains.kotlin.ksp.isVisibleFrom
import org.jetbrains.kotlin.ksp.processing.impl.ResolverImpl
import org.jetbrains.kotlin.ksp.symbol.*
import org.jetbrains.kotlin.ksp.symbol.impl.findParentDeclaration
import org.jetbrains.kotlin.ksp.symbol.impl.kotlin.KSNameImpl
import org.jetbrains.kotlin.ksp.symbol.impl.toKSModifiers
import org.jetbrains.kotlin.resolve.OverridingUtil

class KSPropertyDeclarationJavaImpl(val psi: PsiField) : KSPropertyDeclaration {
    companion object {
        private val cache = mutableMapOf<PsiField, KSPropertyDeclarationJavaImpl>()

        fun getCached(psi: PsiField) = cache.getOrPut(psi) { KSPropertyDeclarationJavaImpl(psi) }
    }

    override val origin = Origin.JAVA

    override val annotations: List<KSAnnotation> by lazy {
        psi.annotations.map { KSAnnotationJavaImpl.getCached(it) }
    }

    override val containingFile: KSFile? by lazy {
        KSFileJavaImpl.getCached(psi.containingFile as PsiJavaFile)
    }

    override val extensionReceiver: KSTypeReference? = null

    override val getter: KSPropertyGetter? = null

    override val setter: KSPropertySetter? = null

    override val modifiers: Set<Modifier> by lazy {
        psi.toKSModifiers()
    }

    override val parentDeclaration: KSDeclaration? by lazy {
        psi.findParentDeclaration()
    }

    override val qualifiedName: KSName by lazy {
        KSNameImpl.getCached(psi.name)
    }

    override val simpleName: KSName by lazy {
        KSNameImpl.getCached(psi.name)
    }

    override val typeParameters: List<KSTypeParameter> = emptyList()

    override val type: KSTypeReference? by lazy {
        KSTypeReferenceJavaImpl.getCached(psi.type)
    }

    override fun overrides(overridee: KSPropertyDeclaration): Boolean {
        return false
    }

    override fun isDelegated(): Boolean {
        return false
    }

    override fun <D, R> accept(visitor: KSVisitor<D, R>, data: D): R {
        return visitor.visitPropertyDeclaration(this, data)
    }

}