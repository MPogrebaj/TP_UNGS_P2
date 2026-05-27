package tp;

public class RentaFija extends Inversion {

    private static final double TASA_ANUAL = 0.20;

    public RentaFija(double montoInvertido, int plazoDias) {
        super(montoInvertido, plazoDias, true);
    }

    @Override
    public double calcularResultado() {
        double intereses = getMontoInvertido() * (TASA_ANUAL / 365) * getPlazoDias();
        return getMontoInvertido() + intereses;
    }

    @Override
    public double calcularResultadoPrecancelado() {
        int dias = diasTranscurridos();
        double intereses = getMontoInvertido() * (TASA_ANUAL / 365) * dias;
        return getMontoInvertido() + (intereses / 2);
    }

    @Override
    public String descripcion() {
        return "Renta Fija";
    }
}