import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class ConexionDB {
    protected Connection conexion;
    public ConexionDB(){
        String usr = "root";
        String pwd = "root";
        String url = "jdbc:mysql://localhost:3001/gato";
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url,usr,pwd);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    protected void cerrar(PreparedStatement ps) throws Exception{
        if (ps != null)
        {
            ps.close();
        }
    }
    protected void cerrar (ResultSet rs) throws Exception
    {
        if (rs != null)
        {
            rs.close();
        }
    }
}
