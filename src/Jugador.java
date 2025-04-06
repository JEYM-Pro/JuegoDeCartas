import java.util.Random;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    private Random r = new Random(); // la suerte del jugador

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();

        // Ordenar las cartas por su valor (NombreCarta.ordinal)
        List<Carta> listaCartas = Arrays.asList(cartas);
        Collections.sort(listaCartas, (c1, c2) -> {
            int comparacionPinta = c1.getPinta().ordinal() - c2.getPinta().ordinal();
            if (comparacionPinta == 0) {
                return c1.getNombre().ordinal() - c2.getNombre().ordinal();
            }
            return comparacionPinta;
        });

        int posicion = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : listaCartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron figuras";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador > 1) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            int fila = 0;
            for (int contador : contadores) {
                if (contador > 1) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[fila] + "\n";
                }
                fila++;
            }
        }

        return mensaje;
    }

    
    public int getPuntajeCartasSueltas() {
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        int puntaje = 0;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] == 1) {
                NombreCarta nombre = NombreCarta.values()[i];
                switch (nombre) {
                    case AS:
                    case JACK:
                    case QUEEN:
                    case KING:
                        puntaje += 10;
                        break;
                    default:
                        puntaje += i + 1;
                        break;
                }
            }
        }

        return puntaje;
    }

    public String getEscaleras() {
        String mensaje = "No se encontraron grupos en escalera.";
        List<Carta>[] cartasPorPinta = new ArrayList[Pinta.values().length];

        // Inicializar listas para cada pinta
        for (int i = 0; i < cartasPorPinta.length; i++) {
            cartasPorPinta[i] = new ArrayList<>();
        }

        // Clasificar cartas por pinta
        for (Carta carta : cartas) {
            cartasPorPinta[carta.getPinta().ordinal()].add(carta);
        }

        boolean hayEscaleras = false;
        StringBuilder escalerasEncontradas = new StringBuilder();

        // Verificar escaleras en cada pinta
        for (int i = 0; i < cartasPorPinta.length; i++) {
            List<Carta> cartasDePinta = cartasPorPinta[i];
            if (cartasDePinta.size() >= 3) { // Una escalera requiere al menos 3 cartas
                // Ordenar las cartas por su valor
                Collections.sort(cartasDePinta, (c1, c2) -> c1.getNombre().ordinal() - c2.getNombre().ordinal());

                // Buscar secuencias consecutivas
                List<Carta> escaleraActual = new ArrayList<>();
                for (int j = 0; j < cartasDePinta.size(); j++) {
                    if (escaleraActual.isEmpty() || 
                        cartasDePinta.get(j).getNombre().ordinal() == escaleraActual.get(escaleraActual.size() - 1).getNombre().ordinal() + 1) {
                        escaleraActual.add(cartasDePinta.get(j));
                    } else {
                        if (escaleraActual.size() >= 3) {
                            hayEscaleras = true;
                            escalerasEncontradas.append(describirEscalera(escaleraActual, Pinta.values()[i])).append("\n");
                        }
                        escaleraActual.clear();
                        escaleraActual.add(cartasDePinta.get(j));
                    }
                }

                // Verificar la última escalera
                if (escaleraActual.size() >= 3) {
                    hayEscaleras = true;
                    escalerasEncontradas.append(describirEscalera(escaleraActual, Pinta.values()[i])).append("\n");
                }
            }
        }

        if (hayEscaleras) {
            mensaje = "Se encontraron los siguientes grupos en escalera:\n" + escalerasEncontradas.toString();
        }

        return mensaje;
    }

    private String describirEscalera(List<Carta> escalera, Pinta pinta) {
        StringBuilder descripcion = new StringBuilder(Grupo.values()[escalera.size()].toString() + " de " + pinta + ": ");
        for (Carta carta : escalera) {
            descripcion.append(carta.getNombre()).append(", ");
        }
        return descripcion.substring(0, descripcion.length() - 2); 
    }
}