package org.jalarcon.junit5app.ejemplos.models;

import org.jalarcon.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class CuentaTest {

    @Test
    @DisplayName("Probando nombre de la cuenta")
    void TestNombreCuenta(TestInfo testInfo, TestReporter testReporter) {
        System.out.println("Ejecutando: "+testInfo.getDisplayName() + " "+ testReporter.toString());
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

    /*
    @AfterEach
    @BeforeEach
    @BeforeAll
    @AfterAll
     void beforeAll(){
    instrucciones
    }
    * */

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

    @RepeatedTest(3,"Nombre del test repetido")
    void TestRepetidoCuenta() {
        Cuenta cuenta = new Cuenta("camilo", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertEquals(900, cuenta.getSaldo().intValue());
        assertNotNull(cuenta.getSaldo());
    }

    @ParameterizedTest
    @ValueSource(strings={"100","200","300"})
    void testDineroInsuficienteExceptionCuenta(String monto) {
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
    //@CsvSource()
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

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {

    }
    @Test
    //@DisabledOnOs(OS.X)
    //@DisabledOnJre(JRE.JAVA_X)
    @EnabledOnOs({OS.LINUX,OS.MAC})
    void testSoloMacLinux() {

    }
    @Tag("TestsClass")
    @Nested
    class TestsSO{

    @Test
    void testImprimirVariables() {
        Map<String,String> getEnv = System.getenv();
        getEnv.forEach((x,v)->System.out.println(x+"="+v));
    }
    @Test
    @DisabledIfEnvironmentVariable(named="ENVIRONMENT".,matches = "prod")
    void testSaldoCuentaDev() {
        boolean isDev = 'dev'.equals(System.getProperty("ENV"));
        assumingThat(isDev,()->{
            String actual = exception.getMessage();
            String esperado = "Dinero Insuficiente";
            assertEquals(actual, esperado);

        });
    }

    }

    @Test
    @Timeout(5)
    void TestPruebaTimeOut() {
        TimeUnit.SECONDS.sleep(2);
        //MILLISECONDS
    }

    @Test
    void TestTimeOutAssertions() {
    assertTimeout(Duration.ofSeconds(5),()->{
        TimeUnit.MICROSECONDS.sleep(5);
    });
    }
}