package ru.mipt.npm.integration

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI
import kotlin.math.sin


class MidPointRuleIntegratorTest {
    @Test
    fun testLinearFunction() {
        val integrator = MidPointRuleIntegrator()
        val res = integrator.integrateWithInfo(0.0, 1.0) { 3.0 * it + 1.0 }
        println("The result of linear function integration is ${res}")
        assertEquals(2.5, res.result, 1e-15)
    }
    @Test
    fun testQuadraticFunction() {
        val integrator = MidPointRuleIntegrator()
        val res = integrator.integrateWithInfo(0.0, 1.0) { 3.0 * it * it + 1.0 }
        println("The result of quadratic function integration is ${res}")
        assertEquals(2.0, res.result, 1e-4)
    }
    @Test
    fun testSinFunction() {
        val integrator = MidPointRuleIntegrator()
        val res = integrator.integrateWithInfo(0.0, PI) { sin(it) }
        println("The result of linear function integration is ${res}")
        assertEquals(2.0, res.result, 1e-4)
    }
}