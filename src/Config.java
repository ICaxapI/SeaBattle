import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static int PORT;
    public static String LOGIN;
    public static String IP;

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;
        try {
            propertiesFile = new FileInputStream("src/config");
            properties.load(propertiesFile);
            LOGIN = properties.getProperty("LOGIN");
            PORT = Integer.parseInt(properties.getProperty("PORT"));
            IP = properties.getProperty("IP");
        } catch (FileNotFoundException ex) {
            System.err.println("config не найден");
        } catch (IOException ex) {
            System.err.println("Ошибка чтения файла");
        } finally {
            try {
                propertiesFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
