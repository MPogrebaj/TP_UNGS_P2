package tp;

public class CuentaCorporativa extends Cuenta {
    private final String cuitEmpresa;

    public CuentaCorporativa(String cvu, String alias, String cuitEmpresa) {
        super(cvu, alias);
        this.cuitEmpresa = validarCuit(cuitEmpresa);
    }

    public String consultarCuitEmpresa() {
        return cuitEmpresa;
    }

    @Override
    public String tipo() {
        return "Corporativa";
    }

    @Override
    protected void verificarDeposito(double monto) {
        // Las cuentas corporativas no tienen límite superior de saldo.
    }

    private String validarCuit(String cuit) {
        if (cuit == null || cuit.isBlank()) {
            throw new RuntimeException("CUIT inválido para la cuenta corporativa.");
        }
        return cuit;
    }
}
