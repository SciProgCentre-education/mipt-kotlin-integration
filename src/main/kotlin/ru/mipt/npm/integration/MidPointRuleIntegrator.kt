package ru.mipt.npm.integration

class MidPointRuleIntegrator(
        relTol: Double = DEFAULT_REL_TOL,
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
        for (i in 1..stepsNum){
            sum += function(from + (i - 0.5) * step)
        }
        this._callNum += stepsNum
        return step * sum
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
            sum += function(from + (i - 5.0 / 6.0) * step) + function(from + (i - 1.0 / 6.0) * step)
        }
        this._callNum += 2 * stepsNum
        return (oldResult + sum * step) / 3.0
    }

    override fun genNextIterationGrid(stepsNum: Int, step: Double) = Pair(3 * stepsNum, step / 3.0)
}

