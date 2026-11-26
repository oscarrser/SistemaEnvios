package modelo.estado;

import modelo.excepciones.TransicionInvalidaException;

public class Registrado implements EstadoEnvio {
    public String getNombre() { return "REGISTRADO"; }
    public EstadoEnvio pasarAEnRuta() { return new EnRuta(); }
    public EstadoEnvio entregar() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("El envío debe pasar primero a EN RUTA.");
    }
    public EstadoEnvio devolver() throws TransicionInvalidaException {
        throw new TransicionInvalidaException("El envío debe pasar primero a EN RUTA.");
    }
    public boolean permiteCorreccion() { return true; }
}
