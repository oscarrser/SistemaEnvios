package modelo.estado;

public final class EstadoFactory {
    private EstadoFactory() { }

    public static EstadoEnvio crear(String nombre) {
        if (nombre == null) throw new IllegalArgumentException("Estado nulo.");
        switch (nombre.trim().toUpperCase()) {
            case "REGISTRADO": return new Registrado();
            case "EN RUTA":
            case "ENRUTA": return new EnRuta();
            case "ENTREGADO": return new Entregado();
            case "DEVUELTO": return new Devuelto();
            default: throw new IllegalArgumentException("Estado desconocido: " + nombre);
        }
    }
}
