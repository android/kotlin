// WITH_RUNTIME
// TEST PROCESSOR: PlatformDeclarationProcessor
// EXPECTED:
// Actual.kt : Clazz : true : false : Expect.kt
// Actual.kt : Clazz.foo : true : false : Expect.kt
// Actual.kt : Klass : true : false : Expect.kt
// Actual.kt : bar : true : false : Expect.kt
// Actual.kt : baz : true : false : Expect.kt
// Coffee.java : Coffee : false : false
// Coffee.java : Coffee.baz : false : false
// Coffee.java : Coffee.foo : false : false
// Expect.kt : Clazz : false : true : Actual.kt
// Expect.kt : Clazz.foo : false : false
// Expect.kt : Klass : false : true : Actual.kt
// Expect.kt : bar : false : true : Actual.kt
// Expect.kt : baz : false : true : Actual.kt
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
