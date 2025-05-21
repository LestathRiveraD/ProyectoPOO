import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaServidor extends JFrame
{
    private int victoria1 = 0;
    private int victoria2 = 0;
    Object[][] datos = {
            {"Jugador 1 (O)", victoria1},
            {"Jugador 2 (X)", victoria2}
    };
    private JTable tabla;
    String[] columnas = {"Jugadores", "Puntos"};
    public VistaServidor() {
        int victoria1 = 0;
        int victoria2 = 0;
        setTitle("Tabla de puntos - Gato");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Marcador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        tabla = new JTable(datos, columnas);
        tabla.setFont(new Font("Arial", Font.PLAIN, 15));
        tabla.setRowHeight(30);
        tabla.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }
    public void updateRecord(int[] puntos)
    {
        datos[0][1] = puntos[0];
        datos[1][1] = puntos[1];
        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        tabla.setModel(model);
        System.out.println(puntos[0] + " " + puntos[1]);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaServidor());
    }
}