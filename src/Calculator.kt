class Calculator {
    fun calculate(a: Int, b: Int, operation: String): String {
        return when (operation) {
            "ADD" -> calc(a, b, ::add).toString()
            "SUB" -> calc(a, b, ::sub).toString()
            "DIV" -> calc(a.toDouble(), b.toDouble(), ::div).toString()
            "MULTI" -> calc(a, b, ::multi).toString()
            else -> {
                "Something went wrong"
            }
        }
    }

    // A Calculator (functional programming)
    private fun <T> calc(a: T, b: T, operation: (T, T) -> T): T {
        return operation(a, b)
    }

    private fun add(a: Int, b: Int): Int = a + b
    private fun sub(a: Int, b: Int): Int = a - b
    private fun div(a: Double, b: Double): Double = a / b
    private fun multi(a: Int, b: Int): Int = a * b
}