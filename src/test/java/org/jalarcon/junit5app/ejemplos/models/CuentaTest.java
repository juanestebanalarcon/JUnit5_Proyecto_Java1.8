package org.jalarcon.junit5app.ejemplos.models;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void TestNombreCuenta() {
        Cuenta cuenta = new Cuenta("jose",new BigDecimal("100.200"));
        cuenta.setPersona("Juan");
        String esperado ="Juan";
        String actual = cuenta.getPersona();
        //Probar con Assertions.
        assertEquals(esperado,actual);
        assertTrue(actual.equals("Juan"));
    }
    @Test
    void TestSaldoCuenta(){
        Cuenta cuenta = new Cuenta("jose",new BigDecimal("1000.12345"));
        assertEquals(1000.12345,cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO)<0);


    }
}