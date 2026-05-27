package tp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Billetera implements IBilletera {
	private Map<String, Usuario> usuariosPorDni;
    private Map<String, Empresa> empresasPorCuit;
    private Map<String, Cuenta> cuentasPorCvu;
    private Map<String, Cuenta> cuentasPorAlias;
    private Map<String, List<Cuenta>> cuentasPorUsuario;
    private List<Actividad> actividadesGlobales;

	public Billetera() {
		this.usuariosPorDni = new HashMap<>();
        this.empresasPorCuit = new HashMap<>();
        this.cuentasPorCvu = new HashMap<>();
        this.cuentasPorAlias = new HashMap<>();
        this.cuentasPorUsuario = new HashMap<>();
        this.actividadesGlobales = new ArrayList<>();
	}

	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
			String nombreContacto) {

		if (empresasPorCuit.containsKey(cuit)){
			throw new IllegalArgumentException("La empresa ya esta registrada.");
		}

		Empresa empresa = new Empresa(cuit, nombreFantasia, telefono, email, nombreContacto);

		empresasPorCuit.put(cuit, empresa);
	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
		
		Empresa empresa = empresasPorCuit.get(cuitEmpresa);

		if (empresa == null) {
			throw new IllegalArgumentException("Empresa inexistente.");
		}

		empresa.agregarAutorizado(dniAutorizado);

	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) {
		if (usuariosPorDni.containsKey(dni)) {
        	throw new RuntimeException("El usuario ya está registrado.");
    	}
    	Usuario usuario = new Usuario(dni, nombre, telefono, email);
    	usuariosPorDni.put(dni, usuario);
	}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {
		validarUsuarioYAliasParaCreacion(dniUsuario, alias);

		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta = new CuentaRegular(cvu, alias, dniUsuario);

		registrarCuenta(cuenta);
		return cvu;
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
		validarUsuarioYAliasParaCreacion(dniUsuario, alias);
		if (depositoInicial < 500000) throw new IllegalArgumentException("Depósito inicial insuficiente para cuenta premium");

		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta = new CuentaPremium(cvu, alias, dniUsuario, depositoInicial);

		registrarCuenta(cuenta);
		return cvu;
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
		validarUsuarioYAliasParaCreacion(dniUsuario, alias);
		Empresa emp = empresasPorCuit.get(cuitEmpresa);
		if (emp == null) throw new IllegalArgumentException("Empresa no registrada: " + cuitEmpresa);
		if (!emp.estaAutorizado(dniUsuario)) throw new IllegalArgumentException("Usuario no autorizado para empresa: " + dniUsuario);

		String cvu = Utilitarios.generarSiguienteCvu();
		Cuenta cuenta = new CuentaCorporativa(cvu, alias, dniUsuario, cuitEmpresa);

		registrarCuenta(cuenta);
		return cvu;
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) {
		if (!usuariosPorDni.containsKey(dniUsuario)) throw new IllegalArgumentException("Usuario no existe: " + dniUsuario);
		List<Cuenta> cuentas = cuentasPorUsuario.get(dniUsuario);
		List<String> resultado = new ArrayList<>();
		if (cuentas != null) {
			for (Cuenta c : cuentas) {
				resultado.add(c.tipo + ": " + c.alias + " (" + c.cvu + ")");
			}
		}
		return resultado;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) {
		Cuenta c = cuentasPorCvu.get(cvu);
		if (c == null) throw new IllegalArgumentException("Cuenta no encontrada: " + cvu);
		return c.saldo;
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
		// TODO Auto-generated method stub

	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) {
		// TODO Auto-generated method stub

	}

	@Override
	public String consultarCvu(String alias) {
		Cuenta c = cuentasPorAlias.get(alias);
		if (c == null) throw new IllegalArgumentException("Alias no registrado: " + alias);
		return c.cvu;
	}
	private void validarUsuarioYAliasParaCreacion(String dniUsuario, String alias) {
		if (dniUsuario == null || dniUsuario.isEmpty()) {
			throw new IllegalArgumentException("DNI inválido");
		}
		if (alias == null || alias.isEmpty()) {
			throw new IllegalArgumentException("Alias inválido");
		}
		if (!usuariosPorDni.containsKey(dniUsuario)) {
			throw new IllegalArgumentException("Usuario no existe: " + dniUsuario);
		}
		if (cuentasPorAlias.containsKey(alias)) {
			throw new IllegalArgumentException("Alias ya registrado: " + alias);
		}
	}

	private void registrarCuenta(Cuenta cuenta) {
		cuentasPorCvu.put(cuenta.consultarCvu(), cuenta);
		cuentasPorAlias.put(cuenta.consultarAlias(), cuenta);
		cuentasPorUsuario.computeIfAbsent(cuenta.consultarDniTitular(), k -> new ArrayList<>()).add(cuenta);
	}
	@Override
	public List<String> consultarHistorialGlobal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) {
		// TODO Auto-generated method stub
		return null;
	}

}