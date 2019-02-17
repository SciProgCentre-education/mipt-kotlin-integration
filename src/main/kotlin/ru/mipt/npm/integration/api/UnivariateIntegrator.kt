package ru.mipt.npm.integration.api

interface UnivariateIntegrator {
    fun integrate(
        from: Double,
        to: Double,
        step: Double = (to - from) / DEFAULT_STEPS,
        function: (Double) -> Double
    ): Double

    companion object {
        const val DEFAULT_STEPS = 100
    }
}