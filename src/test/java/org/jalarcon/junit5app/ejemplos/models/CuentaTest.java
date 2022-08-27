package org.jalarcon.junit5app.ejemplos.models;

import org.jalarcon.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    @DisplayName("Probando nombre de la cuenta")
    void TestNombreCuenta() {
        Cuenta cuenta = new Cuenta("jose", new BigDecimal("100.200"));
        cuenta.setPersona("Juan");
        String esperado = "Juan";
        String actual = cuenta.getPersona();
        //Probar con Assertions.
        assertEquals(esperado, actual,()->"El nombre de la cuenta no es la esperada");
        assertTrue(actual.equals("Juan"));
        assertNotNull(actual,()->"La cuenta no puede ser nula");

    }

    @Test
    void TestSaldoCuenta() {
        Cuenta cuenta = new Cuenta("jose", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("juanes", new BigDecimal("8900.997"));
        Cuenta cuenta2 = new Cuenta("juanes", new BigDecimal("8900.997"));
        assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2);

    }

    @Test
    @Disabled
    @DisplayName("Probar transferencia debito")
    void TestDebitoCuenta() {
        //fail();
        Cuenta cuenta = new Cuenta("camilo", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertEquals(900, cuenta.getSaldo().intValue());
        assertNotNull(cuenta.getSaldo());
    }

    @Test
    void TestCreditoCuenta() {
        Cuenta cuenta = new Cuenta("camilo", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertEquals(900, cuenta.getSaldo().intValue());
        assertNotNull(cuenta.getSaldo());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("camilo", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(actual, esperado);

    }

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("BBVA");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));
        //usando assertAll() se puede agrupar varios asserts
        assertAll(() -> {
            assertEquals("Andres", banco.getCuentas().stream()
                    .filter(c -> c.getPersona().equals("Andres"))
                    .findFirst().get().getPersona());

        }, () -> {
            assertTrue(banco.getCuentas().stream()
                    .anyMatch(c -> c.getPersona().equals("Andres")));
        }, () -> {
            assertEquals(2, banco.getCuentas().size());
        }, () -> {
            assertEquals("3000", cuenta1.getSaldo().toPlainString());
        }, () -> {
            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        });

    }
}