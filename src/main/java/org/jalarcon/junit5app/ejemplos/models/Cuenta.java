package org.jalarcon.junit5app.ejemplos.models;

import java.math.BigDecimal;

public class    Cuenta {
    private String persona;
    private BigDecimal saldo;

    public String getPersona() {
        return persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }
}
