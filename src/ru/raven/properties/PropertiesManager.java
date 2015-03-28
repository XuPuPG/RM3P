package ru.raven.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesManager {

    private final Properties properties = new Properties();
    private final File propertiesFile = new File("temp.xml");
    private static final PropertiesManager instance = new PropertiesManager();

    public static void setProperty(String key, String value) {
        try (FileOutputStream fos = new FileOutputStream(getPropertiesFile())) {
            Properties prop = getProperties();
            prop.setProperty(key, value);
            prop.store(fos, "AUTO GENERATED SETTINGS FILE");
        } catch (IOException ex) {
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getProperty(String key) {
        try (FileInputStream fis = new FileInputStream(getPropertiesFile())) {
            Properties prop = getProperties();  
            prop.load(fis);
            return prop.getProperty(key);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Properties getProperties() {
        return instance.properties;
    }

    public static File getPropertiesFile() {
        return instance.propertiesFile;
    }
}
