package ru.listtask.utils.managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ConfProperties {
    private static ConfProperties confProperties = null;
    private FileInputStream fileInputStream;
    private static Properties property;
    private String file = "conf.properties";
        
    private ConfProperties() {
        try {
            //указание пути до файла с настройками
            fileInputStream = new FileInputStream(file);
            property = new Properties();
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace(); }
        }
    }
    
    public static ConfProperties getConfProperties() {
        if(confProperties == null) {
            confProperties = new ConfProperties();
        }
        return confProperties;
    }
    
    public String getProperty(String key) {
        return property.getProperty(key);
    }
    
    public int getPropertyInt(String key) {
        int num = Integer.parseInt(property.getProperty(key));
        return num;
    }
    
    public void setProperty(String key, String value) {
        property.setProperty(key, value);
    }
    
    public void savePropertyToFile() {
        try (OutputStream output = new FileOutputStream(file)) {
            property.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
