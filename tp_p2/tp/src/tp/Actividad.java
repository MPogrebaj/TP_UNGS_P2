package tp;

import java.time.LocalDate;

public abstract class Actividad {

    private final LocalDate fecha;
    private final Cuenta cuentaOrigen;
    private final double monto;
    private final boolean aprobada;
/*uso protected porque actividad esta pensado para herencia*/
    protected Actividad(Cuenta cuentaOrigen, double monto, boolean aprobada) {

        if (cuentaOrigen == null) {
            throw new IllegalArgumentException("Cuenta origen inválida.");
        }

        if (monto <= 0) {
            throw new IllegalArgumentException("Monto inválido.");
        }

        this.fecha = Utilitarios.hoy();
        this.cuentaOrigen = cuentaOrigen;
        this.monto = monto;
        this.aprobada = aprobada;
    }

    public LocalDate consultarFecha() {
        return fecha;
    }

    public Cuenta consultarCuentaOrigen() {
        return cuentaOrigen;
    }

    public double consultarMonto() {
        return monto;
    }

    public boolean fueAprobada() {
        return aprobada;
    }

    public String consultarEstado() {
        return aprobada ? "Aprobado" : "Rechazado";
    }

    public abstract String descripcion();

    @Override
    public String toString() {
        return descripcion();
    }
}