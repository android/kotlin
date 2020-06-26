import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.jvm.tasks.Jar

description = "Ksp - Symbol processing for Kotlin"

plugins {
    kotlin("jvm")
}

val packedJars by configurations.creating

dependencies {
    packedJars(project(":kotlin-symbol-processing")) { isTransitive = false }
    packedJars(project(":kotlin-symbol-processing-api")) { isTransitive = false }
    packedJars(project(":kotlin-ksp-gradle")) { isTransitive = false }
    if (hasProperty("kspBaseVersion")) {
        val kspBaseVersion = properties["kspBaseVersion"] as String
        implementation(kotlin("stdlib", kspBaseVersion))
        runtime(kotlin("compiler-embeddable", kspBaseVersion))
    } else {
        compile(kotlinStdlib())
        runtime(projectRuntimeJar(":kotlin-compiler-embeddable"))
    }
}

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

projectTest(parallel = true) {
    workingDir = projectDir
}

publish()

tasks.named<Jar>("jar").configure {
    classifier = "base"
}

runtimeJar(rewriteDepsToShadedCompiler(
    tasks.register<ShadowJar>("shadowJar") {
        from(packedJars)
    }
))

sourcesJar()
javadocJar()
