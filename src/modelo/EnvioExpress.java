package modelo;

public class EnvioExpress extends Envio implements Asegurable {
    public EnvioExpress(String guia, String remitente, String destinatario, String destino,
                        double peso, double largo, double ancho, double alto, double valor) {
        super(guia, remitente, destinatario, destino, peso, largo, ancho, alto, valor);
    }
    @Override public String getTipo() { return "EXPRESS"; }
    @Override public double calcularFlete() { return 15000 + 3500 * calcularPesoFacturable(); }
    @Override public double calcularSeguro() { return Math.max(5000, getValorDeclarado() * 0.01); }
}
