// WITH_RUNTIME
// TEST PROCESSOR: PlatformDeclarationProcessor
// EXPECTED:
// Actual.kt : Clazz : true : false : Subject isn't declared as expect. : Expect.kt
// Actual.kt : Clazz.foo : true : false : Subject isn't declared as expect. : Expect.kt
// Actual.kt : Klass : true : false : Subject isn't declared as expect. : Expect.kt
// Actual.kt : bar : true : false : Subject isn't declared as expect. : Expect.kt
// Actual.kt : baz : true : false : Subject isn't declared as expect. : Expect.kt
// Coffee.java : Coffee : false : false : Not a Kotlin class, function, property or typealias : Not a Kotlin class, function, property or typealias
// Coffee.java : Coffee.baz : false : false : Not a Kotlin class, function, property or typealias : Not a Kotlin class, function, property or typealias
// Coffee.java : Coffee.foo : false : false : Not a Kotlin class, function, property or typealias : Not a Kotlin class, function, property or typealias
// Expect.kt : Clazz : false : true : Actual.kt : Subject isn't declared as actual.
// Expect.kt : Clazz.foo : false : false : Subject isn't declared as expect. : Subject isn't declared as actual.
// Expect.kt : Klass : false : true : Actual.kt : Subject isn't declared as actual.
// Expect.kt : bar : false : true : Actual.kt : Subject isn't declared as actual.
// Expect.kt : baz : false : true : Actual.kt : Subject isn't declared as actual.
// END

// FILE: Expect.kt
expect class Clazz {
    fun foo(): String
}

expect fun bar(): String
expect val baz: String
expect class Klass

// FILE: Actual.kt
actual class Clazz {
    actual fun foo(): String = "foo"
}

actual fun bar(): String = "bar"
actual val baz: String = "baz"
actual typealias Klass = String

// FILE: Coffee.java
class Coffee {
    String foo() {
        return null
    }

    String baz = null
}
