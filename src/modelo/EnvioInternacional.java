package modelo;

public class EnvioInternacional extends Envio implements Asegurable {
    public EnvioInternacional(String guia, String remitente, String destinatario, String destino,
                              double peso, double largo, double ancho, double alto, double valor) {
        super(guia, remitente, destinatario, destino, peso, largo, ancho, alto, valor);
    }
    @Override public String getTipo() { return "INTERNACIONAL"; }
    @Override public double calcularFlete() { return 60000 + 12000 * calcularPesoFacturable(); }
    @Override public double calcularSeguro() { return Math.max(5000, getValorDeclarado() * 0.01); }
    @Override public double calcularArancel() { return getValorDeclarado() * 0.10; }
}
