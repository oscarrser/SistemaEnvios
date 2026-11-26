package modelo.estado;

import modelo.excepciones.TransicionInvalidaException;

public class Devuelto implements EstadoEnvio {
    public String getNombre() { return "DEVUELTO"; }
    public EstadoEnvio pasarAEnRuta() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("DEVUELTO es un estado final.");
    }
    public EstadoEnvio entregar() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("DEVUELTO es un estado final.");
    }
    public EstadoEnvio devolver() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("El envío ya fue DEVUELTO.");
    }
    public boolean permiteCorreccion() { return false; }
}
