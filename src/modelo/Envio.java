package modelo;

import modelo.estado.EstadoEnvio;
import modelo.estado.Registrado;
import modelo.excepciones.DimensionesInvalidasException;
import modelo.excepciones.PesoInvalidoException;
import modelo.excepciones.TransicionInvalidaException;

public abstract class Envio {
    private final String guia;
    private final String remitente;
    private final String destinatario;
    private final String destino;
    private double pesoReal;
    private double largo;
    private double ancho;
    private double alto;
    private final double valorDeclarado;
    private EstadoEnvio estado;

    protected Envio(String guia, String remitente, String destinatario, String destino,
                    double pesoReal, double largo, double ancho, double alto,
                    double valorDeclarado) {
        this.guia = guia;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.destino = destino;
        this.pesoReal = pesoReal;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.valorDeclarado = valorDeclarado;
        this.estado = new Registrado();
    }

    public abstract String getTipo();
    public abstract double calcularFlete();

    public double calcularPesoVolumetrico() {
        return (largo * ancho * alto) / 5000.0;
    }

    public int calcularPesoFacturable() {
        return (int) Math.ceil(Math.max(pesoReal, calcularPesoVolumetrico()));
    }

    public double calcularSeguro() {
        if (this instanceof Asegurable) return ((Asegurable) this).calcularSeguro();
        return 0.0;
    }

    public double calcularArancel() { return 0.0; }

    public double calcularTotal() {
        return calcularFlete() + calcularSeguro() + calcularArancel();
    }

    public void corregirDatos(double peso, double largo, double ancho, double alto)
            throws PesoInvalidoException, DimensionesInvalidasException,
                   TransicionInvalidaException {
        if (!estado.permiteCorreccion()) {
            throw new TransicionInvalidaException(
                "No se pueden corregir datos cuando el envío está " + estado.getNombre() + "."
            );
        }
        validarPeso(peso, getTipo());
        validarDimensiones(largo, ancho, alto);
        this.pesoReal = peso;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
    }

    public static void validarPeso(double peso, String tipo) throws PesoInvalidoException {
        double maximo = "INTERNACIONAL".equalsIgnoreCase(tipo) ? 30.0 : 50.0;
        if (peso <= 0 || peso > maximo) {
            throw new PesoInvalidoException(
                "El peso debe ser mayor que 0 y no superar " + maximo + " kg para " + tipo + "."
            );
        }
    }

    public static void validarDimensiones(double largo, double ancho, double alto)
            throws DimensionesInvalidasException {
        if (largo <= 0 || ancho <= 0 || alto <= 0) {
            throw new DimensionesInvalidasException(
                "Largo, ancho y alto deben ser mayores que cero."
            );
        }
    }

    public void pasarAEnRuta() throws TransicionInvalidaException {
        estado = estado.pasarAEnRuta();
    }

    public void entregar() throws TransicionInvalidaException {
        estado = estado.entregar();
    }

    public void devolver() throws TransicionInvalidaException {
        estado = estado.devolver();
    }

    public void restaurarEstado(String nombre) {
        estado = modelo.estado.EstadoFactory.crear(nombre);
    }

    public String getGuia() { return guia; }
    public String getRemitente() { return remitente; }
    public String getDestinatario() { return destinatario; }
    public String getDestino() { return destino; }
    public double getPesoReal() { return pesoReal; }
    public double getLargo() { return largo; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }
    public double getValorDeclarado() { return valorDeclarado; }
    public String getEstado() { return estado.getNombre(); }
}
