package Twitter.Database;

/**
 * Created by YHWH on 10/28/15.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {
    private Properties props;
    private String url;
    private String user;
    private String passwd;
    private int max; // 連接池中最大Connection數目
    private List<Connection> connections;

    public DBUtil() throws IOException, ClassNotFoundException {
        this("jdbc.properties");
    }

    public DBUtil(String configFile) throws IOException, ClassNotFoundException {
        props = new Properties();
        props.load(new FileInputStream(configFile));

        url = "jdbc:mysql://ec2-52-23-196-65.compute-1.amazonaws.com:3306/teamproject";
        user = "client";
        passwd = "123456";
        max = 20;
        Class.forName("com.mysql.jdbc.Driver");

        connections = new ArrayList<Connection>();
    }

    public synchronized Connection getConnection()
            throws SQLException {
        if(connections.size() == 0) {
            return DriverManager.getConnection(url, user, passwd);
        }
        else {
            int lastIndex = connections.size() - 1;
            return connections.remove(lastIndex);
        }
    }

    public synchronized void closeConnection(Connection conn)
            throws SQLException {
        if(connections.size() == max) {
            conn.close();
        }
        else {
            connections.add(conn);
        }
    }
}
