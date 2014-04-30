/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmcm.logosapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Juan
 */
public class Parameters {

    // Lee los datos del Archivo de configuracion
    protected final static String configFile = "settings.properties";
    protected static final Properties prop = new Properties();

    static {
        try {
            Parameters p = new Parameters();

            p.init();
        } catch (Exception e) {
            System.out.println("Error al cargar archivo de propiedades pathFile = " + configFile);
        }
    }
    public static Integer puerto;
    public static String path_importacion;

    private void init() {
        try {
//            Properties prop = new Properties();

//			is = new FileInputStream(new File(".").getAbsolutePath() +"//"+Parameters.configFile); 
//            is = this.getClass().getResourceAsStream("/etc/" + Parameters.configFile);

            prop.load(new FileInputStream("etc/" + Parameters.configFile));

            Parameters.puerto = Integer.parseInt(prop.getProperty("puerto"));
            Parameters.path_importacion = prop.getProperty("path_importacion");

        } catch (Exception e) {
            System.out.println("Error al cargar archivo de propiedades path file: " + configFile);
        } finally {
            
        }
    }
}
