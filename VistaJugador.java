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
public class VistaJugador extends JFrame implements ActionListener
{
    //Declaracion de variables. Lista de todos los botones e inicializacion de las imagenes y sus iconos
    private boolean my_turn;
    private int[] coords = {0,0};
    private List<JButton> botones_lista = new ArrayList<>();
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
    private String path = null;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


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
        public void reinicio(Boolean p1) {
            for (int i = 0; i < botones_lista.size(); i++)
            {
                JButton boton = (JButton) botones_lista.get(i);
                boton.setText(String.valueOf(i));
                boton.setIcon(null);
            }
            setMy_turn(p1);
            deshabilitarReinicio();

        }
    };
    
    public VistaJugador()
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
        status.setForeground(Color.decode("#fff3f3"));
        status.setFocusable(false);
        statuspanel.add(status);
        connectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ipInput = new JTextArea("IP del servidor");
        botonMandar = new JButton("Mandar");
        botonMandar.addActionListener(this);
        botonReinicio = new JButton("Reinicio");
        botonReinicio.setBackground(Color.decode("#a07cff"));
        botonMandar.setBackground(Color.decode("#a07cff"));
        botonReinicio.addActionListener(this);
        connectionPanel.add(ipInput);
        connectionPanel.add(botonMandar);
        deshabilitarReinicio();
        this.setBackground(Color.decode("#2c2c2a"));
        statuspanel.add(botonReinicio);


        //For loop que agrega botones a la cuadricula mientras les asigna un id y pone un action listener
        for (int i = 0; i <= 8; i++)
        {
            JButton boton = new JButton();
            boton.setFocusPainted(false);
            boton.setBackground(Color.decode("#a07cff"));
            boton.setOpaque(true);
            boton.addActionListener(this);
            boton.setForeground(new Color(0, 0, 0, 0));
            boton.setText(String.valueOf(i));
            panel.add(boton);
            botones_lista.add(boton);
        }
        status.setBackground(Color.decode("#2c2c2a"));
        panel.setBackground(Color.decode("#2c2c2a"));
        statuspanel.setBackground(Color.decode("#2c2c2a"));
        connectionPanel.setBackground(Color.decode("#2c2c2a"));
        this.add(panel, BorderLayout.CENTER);
        this.add(statuspanel, BorderLayout.NORTH);
        this.add(connectionPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    //Metodo que escucha la accion checa si es el turno y obtiene coordenadas para asignar la imagen al boton
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();
        System.out.println("??? 2");
        if (e.getSource() == botonMandar && !conectado) 
        {
            System.out.println("x");
            try 
            {
                System.out.println("mandando");
                socket = new Socket(ipInput.getText(), 9001);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                // Recibir imagen correspondiente
                path = in.readLine();
                icon = new ImageIcon(path);
                image = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

                if (path.equals("assets/2.png"))
                {
                    rivalIcon = new ImageIcon("assets/1.png");
                    rivalImage = rivalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    System.out.println("Path asignado al rival: assets/1.png" );
                }
                else
                {
                    System.out.println("Path asignado al rival: assets/2.png" );
                    rivalIcon = new ImageIcon("assets/2.png");
                    rivalImage = rivalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                }

                String turn = in.readLine();

                if (turn.equals("Establecido como jugador 1")) {
                    my_turn = true;
                } else {
                    my_turn = false;
                }
                board_interface.setMy_turn(my_turn);
                conectado = true;

                System.out.println("Creando thread de escucha...");
                new Thread(() -> {
                    System.out.println("d");
                    while (true) 
                    {
                        try {
                            String mensaje = in.readLine();
                            System.out.println(mensaje);
                            if (mensaje.startsWith("fin:")) {
                                int resultado = Integer.parseInt(mensaje.substring(4));
                                if (resultado == 1)
                                {
                                    if (path.equals("assets/1.png")) board_interface.gameover("GANASTE");
                                    else board_interface.gameover("PERDISTE");
                                }
                                else if (resultado == 2)
                                {
                                    if (path.equals("assets/2.png")) board_interface.gameover("GANASTE");
                                    else board_interface.gameover("PERDISTE");
                                }
                                else board_interface.gameover("EMPATE");
                            }
                            if (mensaje.equals("reinicio")) {
                                if (path.equals("assets/2.png"))
                                {
                                    board_interface.reinicio(false);
                                }
                                else
                                {
                                    board_interface.reinicio(true);
                                }
                            }
                            int opponent_move = Integer.parseInt(mensaje);
                            board_interface.fillSquare(return_coords(String.valueOf(opponent_move)));
                            board_interface.setMy_turn(true);
                            
                        } 
                        catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        Thread.yield();
                    }
                }).start();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else
        {
            System.out.println("o");
        }
        if (boton.getText() == "Reinicio")
        {
            status.setText("ESPERANDO REINICIO");
            out.println("ESPERANDO REINICIO");
            return;
        }
        if (my_turn == false){
            System.out.println("??? 2");
            return;
        }
        if (boton.getText() == "x")
        {
            System.out.println("???");
            return;
        }

        if (socket != null && e.getSource() != botonMandar)
        {
            boton.setIcon(new ImageIcon(image));
            board_interface.setMy_turn(false);
            System.out.println("Valor del boton:" + boton.getText());
            out.println(boton.getText());
            
            boton.setText("x");
        }
    }

    //Metodo que en base al texto del boton los transforma en un array de int de tamaño dos para actuar como coordenadas
    private int[] return_coords(String coords){
        int n = Integer.parseInt(coords);
        int y = n % 3;
        int x = n / 3;
        return new int[]{x, y};
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
        new VistaJugador();
    }
}
