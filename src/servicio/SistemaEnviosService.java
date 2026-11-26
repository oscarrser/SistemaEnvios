package servicio;

import dao.EnvioDAO;
import fabrica.EnvioFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import modelo.Envio;
import modelo.excepciones.GuiaInvalidaException;
import modelo.excepciones.TransicionInvalidaException;

public class SistemaEnviosService {
    private final EnvioDAO dao;

    public SistemaEnviosService(EnvioDAO dao) {
        this.dao = dao;
    }

    public void registrar(String tipo, String guia, String remitente, String destinatario,
            String destino, double peso, double largo, double ancho, double alto, double valor)
            throws Exception {
        Envio envio = EnvioFactory.crear(tipo, guia, remitente, destinatario,
                destino, peso, largo, ancho, alto, valor);
        if (dao.buscar(guia) != null) throw new GuiaInvalidaException("La guía ya está registrada.");
        dao.insertar(envio);
    }

    public List<Envio> listar() throws SQLException { return dao.listar(); }

    public void eliminar(String guia) throws SQLException { dao.eliminar(guia); }

    public void corregir(String guia, double peso, double largo, double ancho, double alto)
            throws Exception {
        Envio e = dao.buscar(guia);
        if (e == null) throw new GuiaInvalidaException("No existe un envío con esa guía.");
        e.corregirDatos(peso, largo, ancho, alto);
        dao.actualizar(e);
    }

    public void cambiarEstado(String guia, String accion) throws Exception {
        Envio e = dao.buscar(guia);
        if (e == null) throw new GuiaInvalidaException("No existe un envío con esa guía.");
        String anterior = e.getEstado();

        if ("EN RUTA".equals(accion)) e.pasarAEnRuta();
        else if ("ENTREGADO".equals(accion)) e.entregar();
        else if ("DEVUELTO".equals(accion)) e.devolver();
        else throw new TransicionInvalidaException("Acción de estado desconocida.");

        dao.actualizar(e);
        if (!anterior.equals(e.getEstado())) dao.registrarHistorial(guia, e.getEstado());
    }

    public Map<String, Double> consultarTotales() throws SQLException {
        return dao.consultarTotalesPorEstado();
    }

    public List<String> consultarHistorial(String guia) throws SQLException {
        return dao.consultarHistorial(guia);
    }
}
