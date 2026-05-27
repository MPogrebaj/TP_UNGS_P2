package tp;


public class CuentaPremium extends Cuenta {
    public CuentaPremium(String cvu, String alias, double depositoInicial) {
        super(cvu, alias);
        if (depositoInicial < 500_000.0) {
            throw new RuntimeException("El depósito inicial para cuentas premium debe ser de al menos $500.000.");
        }
        depositar(depositoInicial);
    }

    @Override
    public String tipo() {
        return "Premium";
    }

    @Override
    protected void verificarDeposito(double monto) {
        // No hay límite superior para cuentas premium.
    }
}
