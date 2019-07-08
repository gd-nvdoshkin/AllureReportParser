package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigReader {
    private static Properties props = initProperty();

    private static Properties initProperty() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(ConfigReader.class.getClassLoader().getResource("application.properties").getFile());
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    public static String getProperty(String propertyName) {
        return props.getProperty(propertyName, null);
    }

    public static List<String> getPropertyAsList(String propertyName) {
        return Arrays.asList(props.getProperty(propertyName, null).trim().replaceAll(" ", "").split(","));
    }
}
