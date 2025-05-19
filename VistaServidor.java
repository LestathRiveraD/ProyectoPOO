import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class VistaServidor extends JFrame
{
    public VistaServidor() {
        setTitle("Tabla de puntos - Gato");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Marcador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        String[] columnas = {"Jugadores", "Puntos"};
        Object[][] datos = {
                {"Jugador 1 (X)", 0},
                {"Jugador 2 (O)", 0}
        };

        JTable tabla = new JTable(datos, columnas);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        tabla.setRowHeight(30);
        tabla.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaServidor());
    }
}