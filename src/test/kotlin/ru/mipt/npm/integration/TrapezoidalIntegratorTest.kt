package ru.mipt.npm.integration

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI
import kotlin.math.sin


class TrapezoidalIntegratorTest{
    @Test
    fun testSin(){
        val integrator = TrapezoidalIntegrator()
        val res = integrator.integrateWithInfo(0.0, PI){sin(it)}
        println("The result of sin integration is ${res}")
        assertEquals(2.0,res.result,1e-4)
    }
}