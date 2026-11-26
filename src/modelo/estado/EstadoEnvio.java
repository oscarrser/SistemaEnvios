package modelo.estado;

import modelo.excepciones.TransicionInvalidaException;

public interface EstadoEnvio {
    String getNombre();
    EstadoEnvio pasarAEnRuta() throws TransicionInvalidaException;
    EstadoEnvio entregar() throws TransicionInvalidaException;
    EstadoEnvio devolver() throws TransicionInvalidaException;
    boolean permiteCorreccion();
}
