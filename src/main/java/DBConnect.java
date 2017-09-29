import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by jeremyfransen on 9/27/17.
 */
public class DBConnect {

    private static String dbURL;
    private static String userName;
    private static String password;
    private static Connection connection;

    private DBConnect(){
        try{
            Properties properties;
            properties = System.getProperties();
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("config.properties");
            properties.load(inputStream);
            dbURL = properties.getProperty("dataBaseURL");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        setConnection();
    }

    private static void setConnection(){

        try{
            connection = DriverManager.getConnection(dbURL, userName, password);
            //Statement statement = connection.createStatement();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    private static class DBConnectManager{
        private static final DBConnect INSTANCE = new DBConnect();
    }

    private static DBConnect getInstance(){
        try{
            return DBConnectManager.INSTANCE;
        }
        catch (ExceptionInInitializerError e){
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(){
        return getInstance().connection;
    }


}
