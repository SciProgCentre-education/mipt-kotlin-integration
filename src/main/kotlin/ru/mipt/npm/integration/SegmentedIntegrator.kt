package ru.mipt.npm.integration

/**
 * An integrator that allows to split integration region into subregions and integrate them separately
 */
abstract class SegmentedIntegrator : UnivariateIntegrator {

    //TODO replace by parameter
    protected abstract fun buildIntegrator(from: Double, to: Double): UnivariateIntegrator

    override fun integrate(from: Double, to: Double, function: (Double) -> Double): Double {
        return buildIntegrator(from, to).integrate(from, to, function)
    }

    /**
     * Integrate using provided points as region borders.
     */
    fun integrate(vararg borders: Double, function: (Double) -> Double): Double {
        require(borders.size >= 2) { "There should be at least two border parameters" }
        //sort input
        borders.sort()
        var sum = 0.0
        for (i in (0..borders.size - 2)) {
            val a = borders[i]
            val b = borders[i + 1]
            if (a != b) {
                sum += buildIntegrator(a, b).integrate(a, b, function)
            }
        }
        return sum

//        return borders.sorted().asSequence().distinct().zipWithNext()
//            .map { pair ->
//                buildIntegrator(pair.first, pair.second).integrate(pair.first, pair.second, function)
//            }.sum()
    }
}