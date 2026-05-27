package tp;

public class Transferencia extends Actividad {

    private final Cuenta cuentaDestino;

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double monto, boolean aprobada) {

        super(cuentaOrigen, monto, aprobada);

        if (cuentaDestino == null) {
            throw new IllegalArgumentException("Cuenta destino inválida.");
        }

        this.cuentaDestino = cuentaDestino;
    }

    public Cuenta consultarCuentaDestino() {
        return cuentaDestino;
    }

    @Override
    public String descripcion() {

        String estado = fueAprobada() ? "Aprobado" : "Rechazado";

        return "fecha: " + consultarFecha() + "\norigen: " + consultarCuentaOrigen().consultarDniTitular() + " (" + consultarCuentaOrigen().consultarCvu() + ")" + "\ndestino: " + cuentaDestino.consultarDniTitular() + " (" + cuentaDestino.consultarCvu() + ")" + "\nmonto: " + consultarMonto() + "\n" + estado;
    }

    @Override
    public String toString() {
        return descripcion();
    }
}