import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class OnlinePLayer extends JFrame implements ActionListener
{
    //Declaracion de variables. Lista de todos los botones e inicializacion de las imagenes y sus iconos
    private boolean my_turn = true;
    private int[] coords = {0,0};
    private List botones_lista = new ArrayList<JButton>();
    private ImageIcon icon;
    private Image image;
    private ImageIcon rivalIcon;
    private Image rivalImage;
    private JPanel panel;
    private JPanel statuspanel;
    private JTextArea status;
    private JButton botonReinicio;
    private JPanel connectionPanel;
    private JTextArea ipInput;
    private JButton botonMandar;
    private boolean conectado = false;
    String path = null;

    private Socket socket;
    BufferedReader in;
    PrintWriter out;



    //Creación de interfaz Boards que tiene los metodos con los que se puede interactuar
    private Board board_interface = new Board()
    {
        @Override
        public void fillSquare(int[] arr) {
            int index = arr[0] * 3 + arr[1];
            JButton boton = (JButton) botones_lista.get(index);
            boton.setText("x");
            boton.setIcon(new ImageIcon(rivalImage));
            setMy_turn(true);
        }

        public int[] getChange() {
            return coords;
        }

        //control de la variable my turn
        public void setMy_turn(boolean turn)
        {
            if (turn == true)
            {
                status.setText("TU TURNO");
            }
            else
            {
                status.setText("TURNO DE ENEMIGO");
            }
            my_turn = turn;
        }
        public boolean getMy_turn()
        {
            return my_turn;
        }
        public void setChange(int[] change)
        {
            coords = change;
        }

        public void gameover(String message)
        {
            for (int i = 0; i < botones_lista.size(); i++)
            {
                JButton boton = (JButton) botones_lista.get(i);
                boton.setText("x");
            }
            status.setText(message);
            habilitarReinicio();
        }

        @Override
        public boolean isConnected() {
            return conectado;
        }

        public String getStatus()
        {
            return status.getText();
        }

        @Override
        public void reinicio() {
            for (int i = 0; i < botones_lista.size(); i++)
            {
                JButton boton = (JButton) botones_lista.get(i);
                boton.setText(String.valueOf(i));
                boton.setIcon(null);
            }
            deshabilitarReinicio();
        }
    };

    public OnlinePLayer()
    {
        //Creacion de ventara y personalizacion de su vista
        icon = new ImageIcon(path);
        image = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        rivalIcon = new ImageIcon("./assets/2.png");
        rivalImage = rivalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        this.setTitle("GatoPlusPlus");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        panel = new JPanel(new GridLayout(3,3,3,3));
        statuspanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        status = new JTextArea("INGRESA EL IP");
        status.setEditable(false);
        status.setFont(new Font("papyrus", Font.BOLD, 25));
        status.setFocusable(false);
        statuspanel.add(status);
        connectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ipInput = new JTextArea("IP del servidor");
        botonMandar = new JButton("Mandar");
        botonMandar.addActionListener(this);
        botonReinicio = new JButton("Reinicio");
        botonReinicio.addActionListener(this);
        connectionPanel.add(ipInput);
        connectionPanel.add(botonMandar);
        deshabilitarReinicio();
        this.setBackground(Color.GRAY);
        statuspanel.add(botonReinicio);

        //For loop que agrega botones a la cuadricula mientras les asigna un id y pone un action listener
        for (int i = 0; i <= 8; i++)
        {
            JButton boton = new JButton();
            boton.setFocusPainted(false);
            boton.setBackground(Color.WHITE);
            boton.setOpaque(true);
            boton.addActionListener(this);
            boton.setForeground(new Color(0, 0, 0, 0));
            boton.setText("x");
            panel.add(boton);
            botones_lista.add(boton);
        }
        this.add(panel, BorderLayout.CENTER);
        this.add(statuspanel, BorderLayout.NORTH);
        this.add(connectionPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    //Metodo que escucha la accion checa si es el turno y obtiene coordenadas para asignar la imagen al boton
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();
        if (boton.getText().equals("Mandar"))
        {
            try
                    (
                            Socket socket = new Socket(ipInput.getText(), 9001);
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    )
            {
                path = in.readLine();
                System.out.println(path);
                icon = new ImageIcon(path);
            }
            catch (IOException ex) {ex.printStackTrace();}
        }
        if (boton.getText() == "Reinicio")
        {
            status.setText("ESPERANDO REINICIO");
            return;
        }
        if (my_turn == false){
            return;
        }
        if (boton.getText() == "x")
        {
            return;
        }

        if (socket != null)
        {
            boton.setIcon(new ImageIcon(image));
            boton.setText("x");
            board_interface.setMy_turn(false);

            out.println(boton.getText());
        }

    }

    //Metodo que en base al texto del boton los transforma en un array de int de tamaño dos para actuar como coordenadas
    private int[] return_coords(String coords){
        int n = coords.charAt(0) - '0';
        int y = n%3;
        int x = n/3;
        int[] result = {x, y};
        return result;
    }
    //acceso a la interface Board
    public Board getInterface(){
        return board_interface;
    }

    public void deshabilitarReinicio()
    {
        botonReinicio.setVisible(false);
    }

    public void habilitarReinicio()
    {
        botonReinicio.setVisible(true);
    }

    public static void main(String[] args) {
        new OnlinePLayer();
    }
}