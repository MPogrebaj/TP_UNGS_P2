package tp;

public class CuentaRegular extends Cuenta {
    private static final double LIMITE_SALDO = 5_000_000.0;

    public CuentaRegular(String cvu, String alias) {
        super(cvu, alias);
    }

    @Override
    public String tipo() {
        return "Regular";
    }

    @Override
    protected void verificarDeposito(double monto) {
        if (obtenerSaldoDisponible() + monto > LIMITE_SALDO) {
            throw new RuntimeException("El saldo no puede superar los 5.000.000 para cuentas regulares.");
        }
    }
}
