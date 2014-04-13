/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.utils.properties_utils;

import cloudservices.brokerage.policy.crawling_services.crawler4jservice.configuration.Crawler4jConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class PropertiesReader {

    public static Crawler4jConfig loadCrawler4jConfig(String fileName) throws IOException, NumberFormatException {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);
            //load a properties file from class path, inside static method
            prop.load(input);
            Crawler4jConfig config = new Crawler4jConfig(prop);
            return config;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
    
    public static Properties loadProperties(String fileName) 
            throws IOException {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);
            //load a properties file from class path, inside static method
            prop.load(input);
            return prop;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
