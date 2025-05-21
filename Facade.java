import java.util.List;

public class Facade {
    private JuegoDAO dao = new JuegoDAO();
    public void addJuego(int resultado) throws Exception {
        JuegoDTO dto = new JuegoDTO();
        dto.setGanador(String.valueOf(resultado));
        dao.append(dto);
    }
    public int get_resutados() throws Exception {
        List <JuegoDTO> juegos = dao.readALL();
        for (JuegoDTO juego : juegos)
        {
            System.out.println(juego.getGanador());
        }
        return 0;
    }
    public void reset_resutados() throws Exception {
        dao.resetTable();
    }

}
