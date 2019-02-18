package ru.mipt.npm.integration.api

interface UnivariateIntegrator {
    fun integrate(
            from: Double,
            to: Double,
            stepsNum: Int = DEFAULT_STEPS_NUM,
            function: (Double) -> Double
    ): Double

    companion object {
        const val DEFAULT_STEPS_NUM = 40
    }
}

object TrapezoidalIntegrator: UnivariateIntegrator {
    override fun integrate(from: Double, to: Double, stepsNum: Int, function: (Double) -> Double): Double {
        var sum: Double = 0.0
        val step: Double = (to-from)/stepsNum
        for (i in 1..stepsNum) {
            sum += function(from + (i - 1) * step) + function(from + i * step)
        }
        return step * 0.5 * sum
    }
}