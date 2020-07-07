// TEST PROCESSOR: DefaultFunctionProcessor
// EXPECTED:
// funLiteral: false
// funWithBody: false
// emptyFun: true
// foo: false
// bar: true
// contains: true
// equals: false
// END
// FILE: a.kt
interface KTInterface: List<String> {
    fun funLiteral() = 1

    fun funWithBody(): Int {
        return 1
    }

    fun emptyFun()

}

// FILE: C.java
interface C {
    default int foo() {
        return 1;
    }

    int bar()
}