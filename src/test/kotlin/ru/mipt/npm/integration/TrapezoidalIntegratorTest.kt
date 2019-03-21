package ru.mipt.npm.integration

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI
import kotlin.math.sin


class TrapezoidalIntegratorTest {
    @Test
    fun testLinearFunction() {
        val integrator = TrapezoidalIntegrator()
        val res = integrator.integrateWithInfo(0.0, 1.0) { 3.0 * it + 1.0 }
        println("The result of linear function integration is ${res}")
        assertEquals(2.5, res.result, 1e-15)
    }
    @Test
    fun testQuadraticFunction() {
        val integrator = TrapezoidalIntegrator()
        val res = integrator.integrateWithInfo(0.0, 1.0) { 3.0 * it * it + 1.0 }
        println("The result of quadratic function integration is ${res}")
        assertEquals(2.0, res.result, 1e-4)
    }
    @Test
    fun testSinFunction() {
        val integrator = TrapezoidalIntegrator()
        val res = integrator.integrateWithInfo(0.0, PI) { sin(it) }
        println("The result of sin function integration is ${res}")
        assertEquals(2.0, res.result, 1e-4)
    }
}