package ru.mipt.npm.integration

import kotlin.math.abs

/**
 * @param relTol - desired relative tolerance
 * @param absTol - desired absolute tolerance
 * @param iniStepsNum - initial number of grid intervals
 * @param maxIterNum] - Maximum number of the doubling grid resolution
 */
class TrapezoidalIntegrator(
    val relTol : Double = DEFAULT_REL_TOL,
    val absTol : Double = DEFAULT_ABS_TOL,
    val iniStepsNum: Int = DEFAULT_INITIAL_STEPS_NUM,
    val maxIterNum : Int = DEFAULT_MAX_ITER_NUM
): UnivariateIntegrator {
    /**
     * 1D trapezoidal integration.
     * Performs 1d trapezoidal integration using sequence
     * of nested grids until the required accuracy ([relTol]/[absTol])
     * or maximum number of iterations [maxIterNum] is reached.
     */
    override fun integrate(from: Double, to: Double, function: (Double) -> Double): Double {

        require(relTol >= 1e-15) {"Relative tolerance is too small or negative"}
        require(absTol >= Double.MIN_VALUE) {"Absolute tolerance is too small or negative"}
        require(iniStepsNum > 0) {"Initial number of grid steps must be greater than zero "}
        require(maxIterNum > 0) {"Maximum number of iteration must be greater than zero"}

        var err: Double
        var sum: Double = 0.0
        var step: Double = (to-from)/iniStepsNum
        var stepsNum: Int = iniStepsNum
        var start: Double
        var result: Double
        var newResult: Double

        for (i in 1..stepsNum) {
            sum += function(from + (i - 1) * step) + function(from + i * step)
        }
        result = 0.5 * step * sum

        for (iter in 2..maxIterNum) {
            start = from + step / 2.0
            for (i in 1..stepsNum) { sum += 2.0 * function(start + (i - 1) * step) }
            newResult = 0.25 * step * sum
            err = abs(newResult - result)
            if ( err < absTol || err < relTol * abs(newResult) ) return newResult
            result = newResult
            step /= 2.0
            stepsNum *= 2
        }
        return result
    }

    companion object {
        const val DEFAULT_REL_TOL: Double = 1e-8
        const val DEFAULT_ABS_TOL: Double = 1e-8
        const val DEFAULT_INITIAL_STEPS_NUM = 16
        const val DEFAULT_MAX_ITER_NUM: Int = 16
    }
}