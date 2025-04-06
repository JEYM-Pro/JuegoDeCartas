import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private JButton btnRepartir;
    private JButton btnVerificar;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;
    private JTabbedPane tpJugadores;

    private Jugador jugador1 = new Jugador();
    private Jugador jugador2 = new Jugador();

    public FrmJuego() {
        setTitle("Juego de Cartas");
        setSize(600, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        // Inicializar paneles
        pnlJugador1 = new JPanel();
        pnlJugador2 = new JPanel();
        pnlJugador1.setBackground(new Color(153, 255, 51));
        pnlJugador1.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        // Inicializar tabbed pane
        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 40, 550, 170);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raul Vidal", pnlJugador2);
        add(tpJugadores);

        // Botón Repartir
        btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRepartirClick(evt);
            }
        });
        add(btnRepartir);

        // Botón Verificar
        btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnVerificarClick(evt);
            }
        });
        add(btnVerificar);
    }

    private void btnRepartirClick(ActionEvent evt) {
        jugador1.repartir();
        jugador1.mostrar(pnlJugador1);

        jugador2.repartir();
        jugador2.mostrar(pnlJugador2);
    }

    private void btnVerificarClick(ActionEvent evt) {
        String mensaje;
        int puntaje;

        if (tpJugadores.getSelectedIndex() == 0) {
            mensaje = jugador1.getGrupos();
            puntaje = jugador1.getPuntajeCartasSueltas();
            mensaje += "\n" + jugador1.getEscaleras();
        } else {
            mensaje = jugador2.getGrupos();
            puntaje = jugador2.getPuntajeCartasSueltas();
            mensaje += "\n" + jugador2.getEscaleras();
        }

        mensaje += "\nPuntaje por cartas sueltas: " + puntaje + " puntos";
        JOptionPane.showMessageDialog(null, mensaje);
    }
}