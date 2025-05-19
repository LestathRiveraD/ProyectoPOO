import java.io.File;
import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {
        Juego test = new Juego();
        Board p1 = new VistaJugador("assets/1.png", "assets/2.png").getInterface();
        Board p2 = new VistaJugador("assets/2.png", "assets/1.png").getInterface();
        int[] x;
        p2.setMy_turn(false);

        while (true)
        {
            boolean turn1 = p1.getMy_turn();
            boolean turn2 = p2.getMy_turn();

            if (!turn1 && !turn2)
            {
                if (p1.getChange() != null)
                {
                    x = p1.getChange();
                    p1.setChange(null);
                    p2.fillSquare(x);
                    test.updateBoard(x, 1);
                }
                else if (p2.getChange() != null)
                {
                    x = p2.getChange();
                    p2.setChange(null);
                    p1.fillSquare(x);
                    test.updateBoard(x, 2);
                }
                switch (test.getGameState())
                {
                    case -1:
                        //System.out.println("Continue");
                        break;
                    case 0:
                        System.out.println("Empate");
                        p1.gameover("EMPATE");
                        p2.gameover("EMPATE");
                        break;
                    case 1:
                        System.out.println("Circle won");
                        p1.gameover("GANASTE");
                        p2.gameover("PERDISTE");
                        break;
                    case 2:
                        System.out.println("Cross won");
                        p1.gameover("PERDISTE");
                        p2.gameover("GANASTE");
                        break;
                }
            }
            if (p1.getStatus().equals("ESPERANDO REINICIO") && p2.getStatus().equals("ESPERANDO REINICIO"))
            {
                p1.reinicio();
                p2.reinicio();
                p1.setMy_turn(true);
                p2.setMy_turn(false);
                test.clearBoard();
            }
            Thread.yield();
        }
    }
}