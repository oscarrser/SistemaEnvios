package modelo.estado;

import modelo.excepciones.TransicionInvalidaException;

public class Entregado implements EstadoEnvio {
    public String getNombre() { return "ENTREGADO"; }
    public EstadoEnvio pasarAEnRuta() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("ENTREGADO es un estado final.");
    }
    public EstadoEnvio entregar() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("El envío ya fue ENTREGADO.");
    }
    public EstadoEnvio devolver() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("ENTREGADO es un estado final.");
    }
    public boolean permiteCorreccion() { return false; }
}
