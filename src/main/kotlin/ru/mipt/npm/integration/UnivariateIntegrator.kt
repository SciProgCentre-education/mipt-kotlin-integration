package ru.mipt.npm.integration

interface UnivariateIntegrator {
    /**
     * Returns the integral of the given function over the interval (from, to).
     * @param from - starting point of the integration domain
     * @param to   - end point of the integration domain
     * @param function - function to be integrated
     */
    fun integrate(
            from: Double,
            to: Double,
            function: (Double) -> Double
    ): Double


}