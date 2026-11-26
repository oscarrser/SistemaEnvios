package sistemaenvios;

import dao.impl.EnvioDAOImpl;
import servicio.SistemaEnviosService;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class SistemaEnvios {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaEnviosService servicio = new SistemaEnviosService(new EnvioDAOImpl());
            new VentanaPrincipal(servicio).setVisible(true);
        });
    }
}
