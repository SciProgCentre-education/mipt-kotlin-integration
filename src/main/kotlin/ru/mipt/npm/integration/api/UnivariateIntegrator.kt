package ru.mipt.npm.integration.api

interface UnivariateIntegrator {
    /**
     * Returns the integral of the given function over the interval (from, to).
     * from - starting point of the integration domain
     * to   - end point of the integration domain
     * relTol, absTol - desired relative and absolute tolerance
     * iniStepsNum - initial number of grid intervals
     * maxIterNum] - Maximum number of the doubling grid resolution
     * function - function to be integrated
     */
    fun integrate(
            from: Double,
            to: Double,
            relTol : Double = DEFAULT_REL_TOL,
            absTol : Double = DEFAULT_ABS_TOL,
            iniStepsNum: Int = DEFAULT_INITIAL_STEPS_NUM,
            maxIterNum : Int = DEFAULT_MAX_ITER_NUM,
            function: (Double) -> Double
    ): Double

    companion object {
        const val DEFAULT_REL_TOL: Double = 1e-8
        const val DEFAULT_ABS_TOL: Double = 1e-8
        const val DEFAULT_INITIAL_STEPS_NUM = 16
        const val DEFAULT_MAX_ITER_NUM: Int = 16
    }
}