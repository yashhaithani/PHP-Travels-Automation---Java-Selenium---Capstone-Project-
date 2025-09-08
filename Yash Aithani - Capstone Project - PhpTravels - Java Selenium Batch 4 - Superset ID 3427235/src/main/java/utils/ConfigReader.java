package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public static Properties prop;

    /**
     * Load config.properties file
     */
    public  Properties initProperties() {
        prop = new Properties();
        try {
            FileInputStream ip = new FileInputStream("src/test/resources\\config.properties");
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * Get property value by key
     */
    public String getProperty(String key) {
        if (prop == null) {
            initProperties();
        }
        return prop.getProperty(key);
    }
    
    
    // create a methods for properties
    public String getappurl() {
    	return prop.getProperty("browserurl");
    }
    
    
    public String getusername() {
    	return prop.getProperty("username");
    	
    }
    
    
    public String getpassword() {
    	return prop.getProperty("password");
    }
    
    
    
    
    
}
