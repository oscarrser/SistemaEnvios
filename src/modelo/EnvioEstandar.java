package modelo;

public class EnvioEstandar extends Envio {
    public EnvioEstandar(String guia, String remitente, String destinatario, String destino,
                         double peso, double largo, double ancho, double alto, double valor) {
        super(guia, remitente, destinatario, destino, peso, largo, ancho, alto, valor);
    }
    @Override public String getTipo() { return "ESTANDAR"; }
    @Override public double calcularFlete() { return 8000 + 2000 * calcularPesoFacturable(); }
}
