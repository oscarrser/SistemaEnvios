package fabrica;

import modelo.*;
import modelo.excepciones.*;

public class EnvioFactory {
    private EnvioFactory() { }

    public static Envio crear(String tipo, String guia, String remitente,
            String destinatario, String destino, double peso, double largo,
            double ancho, double alto, double valor)
            throws GuiaInvalidaException, PesoInvalidoException,
                   DimensionesInvalidasException, DatoInvalidoException {

        if (guia == null || !guia.matches("PL-[0-9]{6}")) {
            throw new GuiaInvalidaException("La guía debe tener el formato PL-000001.");
        }
        if (tipo == null || remitente == null || destinatario == null || destino == null
                || remitente.trim().isEmpty() || destinatario.trim().isEmpty()
                || destino.trim().isEmpty()) {
            throw new DatoInvalidoException("Tipo, remitente, destinatario y destino son obligatorios.");
        }
        if (valor < 0) throw new DatoInvalidoException("El valor declarado no puede ser negativo.");

        String tipoNormalizado = tipo.trim().toUpperCase();
        if (!tipoNormalizado.equals("ESTANDAR")
                && !tipoNormalizado.equals("EXPRESS")
                && !tipoNormalizado.equals("INTERNACIONAL")) {
            throw new DatoInvalidoException("Tipo de envío no válido.");
        }

        Envio.validarPeso(peso, tipoNormalizado);
        Envio.validarDimensiones(largo, ancho, alto);

        switch (tipoNormalizado) {
            case "ESTANDAR":
                return new EnvioEstandar(guia, remitente, destinatario, destino,
                        peso, largo, ancho, alto, valor);
            case "EXPRESS":
                return new EnvioExpress(guia, remitente, destinatario, destino,
                        peso, largo, ancho, alto, valor);
            default:
                return new EnvioInternacional(guia, remitente, destinatario, destino,
                        peso, largo, ancho, alto, valor);
        }
    }
}
