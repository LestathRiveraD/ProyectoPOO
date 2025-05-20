import java.sql.*;

public class ConexionMySQLDocker {

    private static final String URL = "jdbc:mysql://localhost:3306/usuarios_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "1234";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA)) {

            crearTablaUsuarios(conn);
            listarUsuarios(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void crearTablaUsuarios(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void insertarUsuario(Connection conn, String nombre) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            int filas = pstmt.executeUpdate();
        }
    }

    private static void listarUsuarios(Connection conn) throws SQLException {
        String sql = "SELECT id, nombre FROM usuarios";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                System.out.println(" - [" + id + "] " + nombre);
            }
        }
    }
}
