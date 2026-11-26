package dao.impl;

import conexion.Conexion;
import dao.EnvioDAO;
import fabrica.EnvioFactory;
import java.sql.*;
import java.util.*;
import modelo.Envio;

public class EnvioDAOImpl implements EnvioDAO {

    private Envio reconstruir(ResultSet rs) throws SQLException {
        try {
            Envio e = EnvioFactory.crear(
                rs.getString("tipo"), rs.getString("guia"),
                rs.getString("remitente"), rs.getString("destinatario"),
                rs.getString("destino"), rs.getDouble("peso_real"),
                rs.getDouble("largo"), rs.getDouble("ancho"),
                rs.getDouble("alto"), rs.getDouble("valor_declarado")
            );
            e.restaurarEstado(rs.getString("estado"));
            return e;
        } catch (Exception ex) {
            throw new SQLException("Error reconstruyendo envío: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void insertar(Envio e) throws SQLException {
        String sql = "INSERT INTO envios "
            + "(guia,tipo,remitente,destinatario,destino,peso_real,largo,ancho,alto,valor_declarado,estado) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            llenar(ps, e);
            ps.executeUpdate();
        }
        registrarHistorial(e.getGuia(), e.getEstado());
    }

    private void llenar(PreparedStatement ps, Envio e) throws SQLException {
        ps.setString(1, e.getGuia());
        ps.setString(2, e.getTipo());
        ps.setString(3, e.getRemitente());
        ps.setString(4, e.getDestinatario());
        ps.setString(5, e.getDestino());
        ps.setDouble(6, e.getPesoReal());
        ps.setDouble(7, e.getLargo());
        ps.setDouble(8, e.getAncho());
        ps.setDouble(9, e.getAlto());
        ps.setDouble(10, e.getValorDeclarado());
        ps.setString(11, e.getEstado());
    }

    @Override
    public void actualizar(Envio e) throws SQLException {
        String sql = "UPDATE envios SET peso_real=?,largo=?,ancho=?,alto=?,estado=? WHERE guia=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDouble(1, e.getPesoReal());
            ps.setDouble(2, e.getLargo());
            ps.setDouble(3, e.getAncho());
            ps.setDouble(4, e.getAlto());
            ps.setString(5, e.getEstado());
            ps.setString(6, e.getGuia());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(String guia) throws SQLException {
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM envios WHERE guia=?")) {
            ps.setString(1, guia);
            ps.executeUpdate();
        }
    }

    @Override
    public Envio buscar(String guia) throws SQLException {
        String sql = "SELECT * FROM envios WHERE guia=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, guia);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? reconstruir(rs) : null;
            }
        }
    }

    @Override
    public List<Envio> listar() throws SQLException {
        List<Envio> lista = new ArrayList<>();
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM envios ORDER BY guia");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(reconstruir(rs));
        }
        return lista;
    }

    @Override
    public Map<String, Double> consultarTotalesPorEstado() throws SQLException {
        Map<String, Double> resultado = new LinkedHashMap<>();
        String sql = "SELECT estado, COUNT(*) cantidad, "
            + "SUM(CASE tipo "
            + "WHEN 'ESTANDAR' THEN 8000+2000*CEIL(GREATEST(peso_real,largo*ancho*alto/5000)) "
            + "WHEN 'EXPRESS' THEN 15000+3500*CEIL(GREATEST(peso_real,largo*ancho*alto/5000))+GREATEST(5000,valor_declarado*0.01) "
            + "ELSE 60000+12000*CEIL(GREATEST(peso_real,largo*ancho*alto/5000))+GREATEST(5000,valor_declarado*0.01)+valor_declarado*0.10 "
            + "END) total FROM envios GROUP BY estado";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("estado") + " (envíos: "
                    + rs.getInt("cantidad") + ")", rs.getDouble("total"));
            }
        }
        return resultado;
    }

    @Override
    public List<String> consultarHistorial(String guia) throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT estado, fecha_hora FROM historial_estados WHERE guia=? ORDER BY fecha_hora";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, guia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("estado") + " - " + rs.getTimestamp("fecha_hora"));
                }
            }
        }
        return lista;
    }

    @Override
    public void registrarHistorial(String guia, String estado) throws SQLException {
        String sql = "INSERT INTO historial_estados(guia,estado,fecha_hora) VALUES(?,?,NOW())";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, guia);
            ps.setString(2, estado);
            ps.executeUpdate();
        }
    }
}
