package ru.mipt.npm.integration

import kotlin.math.abs


/**
 * 1D composite NewtonCotes quadrature integration.
 * Performs 1d numerical integration using sequence
 * of nested grids until the required accuracy ([relTol]/[absTol])
 * or maximum number of iterations [maxIterNum] is reached.
 * @param relTol - desired relative tolerance
 * @param absTol - desired absolute tolerance
 * @param iniStepsNum - initial number of grid intervals
 * @param maxIterNum - Maximum number of the grid resolution refinements
 */

abstract class NewtonCotesIntegrator(
        val relTol: Double,
        val absTol: Double,
        val iniStepsNum: Int,
        val maxIterNum: Int
): UnivariateIntegrator {

    var _callNum: Int = 0

    /**
     * Performs standard NewtonCotes integration at the initial grid
     */
    abstract fun calcFirstIterationIntegral(
            from: Double,
            step: Double,
            stepsNum: Int,
            function: (Double) -> Double
    ): Double
    /**
     * Computes integral using coarse grid integral value
     */
    abstract fun calcNestedGridIntegralCorrection(
            from: Double,
            step: Double,
            stepsNum: Int,
            oldResult: Double,
            function: (Double) -> Double
    ): Double

    /**
     * Generate new grid parameters
     */
    abstract fun genNextIterationGrid(stepsNum: Int, step: Double): Pair<Int, Double>

    data class Result(val result: Double, val tolerance: Double, val callNum: Int)

    /**
     * Provide integration result with info
     */
    fun integrateWithInfo(from: Double, to: Double, function: (Double) -> Double): Result {
        require(relTol >= 1e-15) { "Relative tolerance is too small or negative" }
        require(absTol >= Double.MIN_VALUE) { "Absolute tolerance is too small or negative" }
        require(iniStepsNum > 0) { "Initial number of grid steps must be greater than zero " }
        require(maxIterNum > 0) { "Maximum number of iteration must be greater than zero" }

        var err: Double = Double.MAX_VALUE
        var step: Double = (to - from) / iniStepsNum
        var stepsNum: Int = iniStepsNum

        //internal func calls number counter
        this._callNum = 0

        var result = calcFirstIterationIntegral(from, step, stepsNum, function)

        // Iteration with nested grid quadrature to avoid recalculating the same values
        for (iter in 2..maxIterNum) {

            val newResult = calcNestedGridIntegralCorrection(from, step, stepsNum, result, function)

            err = abs(newResult - result)
            result = newResult
            if (err < absTol || err < relTol * abs(newResult)) break
            val temp = genNextIterationGrid(stepsNum, step)
            stepsNum = temp.first
            step = temp.second
        }
        return Result(result, err, this._callNum)
    }

    override fun integrate(from: Double, to: Double, function: (Double) -> Double) =
            integrateWithInfo(from, to, function).result

    companion object {
        const val DEFAULT_REL_TOL: Double = 1e-8
        const val DEFAULT_ABS_TOL: Double = 1e-8
        const val DEFAULT_INITIAL_STEPS_NUM = 16
        const val DEFAULT_MAX_ITER_NUM: Int = 16
    }
}
