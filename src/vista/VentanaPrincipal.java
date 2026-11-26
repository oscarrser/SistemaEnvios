package vista;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Envio;
import servicio.SistemaEnviosService;

public class VentanaPrincipal extends JFrame {
    private final SistemaEnviosService servicio;
    private final JTextField guia = new JTextField(10);
    private final JTextField remitente = new JTextField(12);
    private final JTextField destinatario = new JTextField(12);
    private final JTextField destino = new JTextField(12);
    private final JTextField peso = new JTextField(6);
    private final JTextField largo = new JTextField(6);
    private final JTextField ancho = new JTextField(6);
    private final JTextField alto = new JTextField(6);
    private final JTextField valor = new JTextField(8);
    private final JComboBox<String> tipo = new JComboBox<>(
        new String[]{"ESTANDAR", "EXPRESS", "INTERNACIONAL"});
    private final DefaultTableModel modelo = new DefaultTableModel(
        new String[]{"Guía","Tipo","Remitente","Destinatario","Destino","Peso real",
            "Peso facturable","Flete","Seguro","Arancel","Total","Estado"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    public VentanaPrincipal(SistemaEnviosService servicio) {
        this.servicio = servicio;
        setTitle("Sistema de Paquetería PaqueLibre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 650);
        setLocationRelativeTo(null);
        construirInterfaz();
        cargarTabla();
    }

    private void construirInterfaz() {
        JPanel formulario = new JPanel(new GridLayout(0, 4, 6, 6));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del envío"));
        agregar(formulario, "Tipo", tipo);
        agregar(formulario, "Guía PL-000001", guia);
        agregar(formulario, "Remitente", remitente);
        agregar(formulario, "Destinatario", destinatario);
        agregar(formulario, "Destino", destino);
        agregar(formulario, "Peso real (kg)", peso);
        agregar(formulario, "Largo (cm)", largo);
        agregar(formulario, "Ancho (cm)", ancho);
        agregar(formulario, "Alto (cm)", alto);
        agregar(formulario, "Valor declarado", valor);

        JButton guardar = new JButton("Guardar");
        JButton corregir = new JButton("Corregir peso/dimensiones");
        JButton eliminar = new JButton("Eliminar");
        JButton enRuta = new JButton("Pasar a En ruta");
        JButton entregar = new JButton("Entregar");
        JButton devolver = new JButton("Devolver");
        JButton totales = new JButton("Totales por estado");
        JButton historial = new JButton("Historial");
        JButton refrescar = new JButton("Actualizar");

        guardar.addActionListener(e -> ejecutar(() -> {
            servicio.registrar(texto(tipo), texto(guia), texto(remitente), texto(destinatario),
                texto(destino), numero(peso), numero(largo), numero(ancho), numero(alto), numero(valor));
            limpiar(); cargarTabla();
            mensaje("Envío registrado correctamente.");
        }));
        corregir.addActionListener(e -> ejecutar(() -> {
            servicio.corregir(texto(guia), numero(peso), numero(largo), numero(ancho), numero(alto));
            cargarTabla(); mensaje("Datos corregidos.");
        }));
        eliminar.addActionListener(e -> ejecutar(() -> {
            String g = guiaSeleccionada();
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar el envío " + g + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                servicio.eliminar(g); cargarTabla(); limpiar();
            }
        }));
        enRuta.addActionListener(e -> cambiarEstado("EN RUTA"));
        entregar.addActionListener(e -> cambiarEstado("ENTREGADO"));
        devolver.addActionListener(e -> cambiarEstado("DEVUELTO"));
        totales.addActionListener(e -> ejecutar(() ->
            JOptionPane.showMessageDialog(this, servicio.consultarTotales().toString(),
                "Número de envíos y total facturado por estado", JOptionPane.INFORMATION_MESSAGE)));
        historial.addActionListener(e -> ejecutar(() -> {
            String g = guiaSeleccionada();
            JOptionPane.showMessageDialog(this, servicio.consultarHistorial(g).toString(),
                "Historial de estados", JOptionPane.INFORMATION_MESSAGE);
        }));
        refrescar.addActionListener(e -> cargarTabla());

        JPanel botones = new JPanel(new FlowLayout());
        for (JButton b : new JButton[]{guardar, corregir, eliminar, enRuta, entregar,
                devolver, totales, historial, refrescar}) botones.add(b);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int r = tabla.getSelectedRow();
                guia.setText(String.valueOf(modelo.getValueAt(r, 0)));
                tipo.setSelectedItem(modelo.getValueAt(r, 1));
                remitente.setText(String.valueOf(modelo.getValueAt(r, 2)));
                destinatario.setText(String.valueOf(modelo.getValueAt(r, 3)));
                destino.setText(String.valueOf(modelo.getValueAt(r, 4)));
                peso.setText(String.valueOf(modelo.getValueAt(r, 5)));
                cargarDimensionesSeleccionadas();
            }
        });

        setLayout(new BorderLayout(5, 5));
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void agregar(JPanel p, String etiqueta, JComponent campo) {
        p.add(new JLabel(etiqueta)); p.add(campo);
    }
    private String texto(JTextField f) { return f.getText().trim(); }
    private String texto(JComboBox<String> c) { return String.valueOf(c.getSelectedItem()); }
    private double numero(JTextField f) {
        try { return Double.parseDouble(texto(f)); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Ingrese un número válido en: " + f.getName()); }
    }
    private String guiaSeleccionada() {
        if (texto(guia).isEmpty()) throw new IllegalArgumentException("Seleccione un envío o escriba su guía.");
        return texto(guia);
    }
    private void cambiarEstado(String estado) {
        ejecutar(() -> { servicio.cambiarEstado(guiaSeleccionada(), estado); cargarTabla(); });
    }
    private void ejecutar(Operacion op) {
        try { op.ejecutar(); }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void cargarTabla() {
        ejecutar(() -> {
            modelo.setRowCount(0);
            List<Envio> envios = servicio.listar();
            for (Envio e : envios) {
                modelo.addRow(new Object[]{e.getGuia(),e.getTipo(),e.getRemitente(),
                    e.getDestinatario(),e.getDestino(),e.getPesoReal(),e.calcularPesoFacturable(),
                    e.calcularFlete(),e.calcularSeguro(),e.calcularArancel(),e.calcularTotal(),e.getEstado()});
            }
        });
    }
    private void cargarDimensionesSeleccionadas() {
        ejecutar(() -> {
            Envio e = servicio.listar().stream()
                .filter(x -> x.getGuia().equals(texto(guia))).findFirst().orElse(null);
            if (e != null) {
                largo.setText(String.valueOf(e.getLargo()));
                ancho.setText(String.valueOf(e.getAncho()));
                alto.setText(String.valueOf(e.getAlto()));
                valor.setText(String.valueOf(e.getValorDeclarado()));
            }
        });
    }
    private void limpiar() {
        for (JTextField f : new JTextField[]{guia,remitente,destinatario,destino,peso,largo,ancho,alto,valor}) f.setText("");
    }
    private void mensaje(String m) {
        JOptionPane.showMessageDialog(this, m, "PaqueLibre", JOptionPane.INFORMATION_MESSAGE);
    }
    @FunctionalInterface private interface Operacion { void ejecutar() throws Exception; }
}
