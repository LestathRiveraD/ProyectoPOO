import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
    public static void main(String[] args) throws IOException 
    {
        ServerSocket servidor = new ServerSocket(9001); // Iniciar servidor

        // Set up player 1's connection
        System.out.println("Esperando conexión con jugador 1...");
        Socket jugador1 = servidor.accept();
        // Set up input and output to server
        BufferedReader in_jugador1 = new BufferedReader(new InputStreamReader(jugador1.getInputStream()));
        PrintWriter out_jugador1 = new PrintWriter(jugador1.getOutputStream(), true);
        out_jugador1.println("assets/1.png");
        out_jugador1.println("Establecido como jugador 1");

        // Set up player 2's connection
        System.out.println("Esperando conexión con jugador 2...");
        Socket jugador2 = servidor.accept();
        // Set up input and output to server
        BufferedReader in_jugador2 = new BufferedReader(new InputStreamReader(jugador2.getInputStream()));
        PrintWriter out_jugador2 = new PrintWriter(jugador2.getOutputStream(), true);
        out_jugador2.println("assets/2.png");
        out_jugador2.println("Establecido como jugador 2");
        
        System.out.println("Se ha establecido conexión con ambos jugadores.\n");

        Juego juego = new Juego();
        int turno = 1;
        String jugada_jugador1;
        String jugada_jugador2;
        int move_number;
        int curPlayer = 1;

        // TODO
        while(true)
        {
            System.out.println("Turno: " + turno++ + "\n");
            
            System.out.println("Esperando movimiento de jugador 1...\n");
            // Movimiento jugador 1
            jugada_jugador1 = in_jugador1.readLine();
            move_number = Integer.parseInt(jugada_jugador1);
            juego.updateBoard(move_number / 3, move_number % 3, 1); // <-- jugador 1
            System.out.println("Recibido del jugador 1: ");
            juego.printBoard();
            out_jugador2.println(jugada_jugador1);

            // Revisar si terminó el juego
            int estado = juego.getGameState();
            if (estado != -1) {
                out_jugador1.println("fin:" + estado);
                out_jugador2.println("fin:" + estado);
                break;
            }

            // Movimiento jugador 2
            jugada_jugador2 = in_jugador2.readLine();
            move_number = Integer.parseInt(jugada_jugador2);
            juego.updateBoard(move_number / 3, move_number % 3, 2); // <-- jugador 2
            System.out.println("Recibido del jugador 2: ");
            juego.printBoard();
            out_jugador1.println(jugada_jugador2);

            // Revisar si terminó el juego
            estado = juego.getGameState();
            if (estado != -1) {
                out_jugador1.println("fin:" + estado);
                out_jugador2.println("fin:" + estado);
                break;
            }

        }
    }
}