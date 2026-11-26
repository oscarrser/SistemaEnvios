package modelo.estado;

import modelo.excepciones.TransicionInvalidaException;

public class EnRuta implements EstadoEnvio {
    public String getNombre() { return "EN RUTA"; }
    public EstadoEnvio pasarAEnRuta() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("El envío ya está EN RUTA.");
    }
    public EstadoEnvio entregar() { return new Entregado(); }
    public EstadoEnvio devolver() { return new Devuelto(); }
    public boolean permiteCorreccion() { return false; }
}
