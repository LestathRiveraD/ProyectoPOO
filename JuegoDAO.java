import java.sql.*;
import java.util.*;
public class JuegoDAO extends ConexionDB {
        private static final String GANADOR = "ganador";
        private static final String ID = "id";
        private static final String SQL_SELECT_ALL = "SELECT * FROM JUEGO";
        private static final String SQL_INSERT = "INSERT INTO JUEGO" +
                "(" + GANADOR +
                ")VALUES (?)";
        private static final String SQL_READ = "SELECT * FROM JUEGO  WHERE" + ID + " = ?";
        private static final String SQL_DELETE = "DELETE FROM JUEGO WHERE " + ID + " = ?";
        private static final String SQL_UPDATE = "UPDATE JUEGO SET" +
                GANADOR + " = ?," +
                "WHERE " + ID + " = ?";
        private static final String SQL_RESET = "TRUNCATE TABLE JUEGO";
        public JuegoDAO() {
            super();
        }
        public List readALL() throws Exception {
            PreparedStatement ps = null;
            ResultSet rs = null;
            List result = new ArrayList<>();
            ps = conexion.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(getObject(rs));
            }
            cerrar(ps);
            cerrar(rs);
            return result;
        }
        public void append(JuegoDTO dto) throws Exception {
            PreparedStatement ps = null;
            ps = conexion.prepareStatement(SQL_INSERT);
            ps.setString(1, dto.getGanador());
            ps.executeUpdate();
            cerrar(ps);
        }
        public void update(JuegoDTO dto, String id) throws Exception {
            PreparedStatement ps = null;
            ps = conexion.prepareStatement(SQL_UPDATE);
            ps.setString(1, dto.getGanador());
            ps.setString(2, id);
            ps.executeUpdate();
            cerrar(ps);
        }
        public void delete(JuegoDTO dto, String id) throws Exception {
            PreparedStatement ps = null;
            ps = conexion.prepareStatement(SQL_DELETE);
            ps.setString(1, id);
            ps.executeUpdate();
            cerrar(ps);
        }
        public JuegoDTO read(JuegoDTO dto) throws Exception {
            PreparedStatement ps = null;
            ResultSet rs = null;
            JuegoDTO result = null;
            ps = conexion.prepareStatement(SQL_READ);
            ps.setString(1, dto.getGanador());
            rs = ps.executeQuery();
            if (rs.next()) {
                result = getObject(rs);
            }
            cerrar(ps);
            cerrar(rs);

            return result;
        }
        private JuegoDTO getObject(ResultSet rs) throws Exception {
            JuegoDTO dtoPersona = new JuegoDTO();
            dtoPersona.setGanador(rs.getString(GANADOR));
            return dtoPersona;
        }

        public void resetTable() throws Exception {
            PreparedStatement ps = null;
            ps = conexion.prepareStatement(SQL_RESET);
            ps.executeUpdate();
            cerrar(ps);
        }
    }

