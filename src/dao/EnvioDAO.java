package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import modelo.Envio;

public interface EnvioDAO {
    void insertar(Envio envio) throws SQLException;
    void actualizar(Envio envio) throws SQLException;
    void eliminar(String guia) throws SQLException;
    Envio buscar(String guia) throws SQLException;
    List<Envio> listar() throws SQLException;
    Map<String, Double> consultarTotalesPorEstado() throws SQLException;
    List<String> consultarHistorial(String guia) throws SQLException;
    void registrarHistorial(String guia, String estado) throws SQLException;
}
