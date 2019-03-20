package ru.mipt.npm.integration

class TrapezoidalIntegrator(relTol: Double = DEFAULT_REL_TOL,
                            absTol: Double = DEFAULT_ABS_TOL,
                            iniStepsNum: Int = DEFAULT_INITIAL_STEPS_NUM,
                            maxIterNum: Int = DEFAULT_MAX_ITER_NUM
) : NewtonCotesIntegrator(relTol, absTol, iniStepsNum, maxIterNum) {
    override fun calcFirstIterationIntegral(
            from: Double,
            step: Double,
            stepsNum: Int,
            function: (Double) -> Double): Double
    {
        var sum = 0.0
        for (i in 1..(stepsNum - 1)) {
            sum += function(from + i * step)
        }
        this._callNum += stepsNum + 1
        return step * (0.5 * function(from) + sum + 0.5 * function(from + stepsNum * step))
    }

    override fun calcNestedGridIntegralCorrection(
            from: Double,
            step: Double,
            stepsNum: Int,
            oldResult: Double,
            function: (Double) -> Double): Double
    {
        var sum = 0.0
        for (i in 1..stepsNum) {
            sum += function(from + (i - 0.5) * step)
        }
        this._callNum += stepsNum
        return (oldResult + sum * step) * 0.5
    }

    override fun genNextIterationGrid(stepsNum: Int, step: Double) = Pair(stepsNum * 2, step * 0.5)
}