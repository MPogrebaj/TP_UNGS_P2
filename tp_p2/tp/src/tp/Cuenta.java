package tp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Cuenta {
    private final String cvu;
    private final String alias;
    private double saldoDisponible;
    private double montoInvertido;
    private final List<String> historial;

    protected Cuenta(String cvu, String alias) {
        this.cvu = validarCvu(cvu);
        this.alias = validarAlias(alias);
        this.saldoDisponible = 0.0;
        this.montoInvertido = 0.0;
        this.historial = new ArrayList<>();
    }

    public String consultarCvu() {
        return cvu;
    }

    public String consultarAlias() {
        return alias;
    }

    public double obtenerSaldoDisponible() {
        return saldoDisponible;
    }

    public double obtenerMontoInvertido() {
        return montoInvertido;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new RuntimeException("El monto a depositar debe ser mayor a cero.");
        }
        verificarDeposito(monto);
        this.saldoDisponible += monto;
    }

    public void retirar(double monto) {
        if (monto <= 0) {
        	throw new RuntimeException("El monto a retirar debe ser mayor a cero.");
        }
        if (monto > saldoDisponible) {
            throw new RuntimeException("Saldo insuficiente.");
        }
        this.saldoDisponible -= monto;
    }

    public void agregarMontoInvertido(double monto) {
        if (monto <= 0) {
            throw new RuntimeException("El monto invertido debe ser mayor a cero.");
        }
        this.montoInvertido += monto;
    }

    public void descontarMontoInvertido(double monto) {
        if (monto <= 0) {
            throw new RuntimeException("El monto a descontar debe ser mayor a cero.");
        }
        if (monto > montoInvertido) {
            throw new RuntimeException("No hay suficiente monto invertido para descontar.");
        }
        this.montoInvertido -= monto;
    }

    public void registrarActividad(String actividad) {
        if (actividad != null && !actividad.isEmpty()) {
            this.historial.add(actividad);
        }
    }

    public List<String> consultarHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public String detallesCuenta() {
        return String.format("%s: %s (%s)", tipo(), alias, cvu);
    }

    protected abstract String tipo();

    protected abstract void verificarDeposito(double monto);

    private String validarCvu(String cvu) {
        if (cvu == null || cvu.isBlank()) {
            throw new RuntimeException("CVU inválido.");
        }
        return cvu;
    }

    private String validarAlias(String alias) {
        if (alias == null || alias.isBlank()) {
            throw new RuntimeException("Alias inválido.");
        }
        if (alias.length() < 3) {
            throw new RuntimeException("Alias inválido.");
        }
        return alias;
    }
}
