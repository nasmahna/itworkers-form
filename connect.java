import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class N196_KoneksiDB {
    private static Connection koneksi;
    
    public static Connection getKoneksi()
    {
        if (koneksi == null)
            try {
                String url = "jdbc:mysql://localhost:3306/rsp_itworkers";
                String username = "root";
                String password = "";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                
                koneksi = DriverManager.getConnection(url, username, password);
                
                
            } 
            catch (SQLException e) 
            {
                System.out.println(e.getMessage());
            }
        return koneksi ;
    }

}
