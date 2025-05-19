import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class VistaJugador extends JFrame implements ActionListener
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
        public VistaJugador(String img, String rivalImg)
        {
            //Creacion de ventara y personalizacion de su vista
            icon = new ImageIcon(img);
            image = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            rivalIcon = new ImageIcon(rivalImg);
            rivalImage = rivalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            this.setTitle("GatoPlusPlus");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setSize(700, 700);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setLayout(new BorderLayout());
            panel = new JPanel(new GridLayout(3,3,3,3));
            statuspanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            status = new JTextArea("TU TURNO");
            status.setEditable(false);
            status.setFont(new Font("papyrus", Font.BOLD, 25));
            status.setFocusable(false);
            statuspanel.add(status);
            botonReinicio = new JButton("Reinicio");
            botonReinicio.addActionListener(this);
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
                boton.setText(String.valueOf(i));
                panel.add(boton);
                botones_lista.add(boton);
            }
            this.add(panel, BorderLayout.CENTER);
            this.add(statuspanel, BorderLayout.NORTH);
            this.setVisible(true);

        }

        //Metodo que escucha la accion checa si es el turno y obtiene coordenadas para asignar la imagen al boton
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton boton = (JButton) e.getSource();
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
            boton.setIcon(new ImageIcon(image));
            coords = return_coords(boton.getText());
            boton.setText("x");
            board_interface.setMy_turn(false);
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
    }
