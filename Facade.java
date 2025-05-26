import java.util.List;

public class Facade {
    private JuegoDAO dao = new JuegoDAO();
    public void addJuego(int resultado) throws Exception {
        JuegoDTO dto = new JuegoDTO();
        dto.setGanador(String.valueOf(resultado));
        dao.append(dto);
    }
    public int[] get_resutados() throws Exception {
        List <JuegoDTO> juegos = dao.readALL();
        int[] x = {0, 0};
        for (JuegoDTO juego : juegos)
        {
            switch (juego.getGanador())
            {
                case "1":
                {
                    x[0]++;
                    break;
                }
                case "2":
                {
                    x[1]++;
                    break;
                }
                default:
                {
                    ;
                }
            }
        }
        return x;
    }
    public void reset_resutados() throws Exception {
        dao.resetTable();
    }

}