/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Yulian Alexis Tobar Rios
 * @author Hellen Valeria Melo Cubides
 */
public class Configuration {

    private Properties propiedades;
    private String archivo;

    public Configuration(String archivo) {
        this.archivo = archivo;
        propiedades = new Properties();
        cargar();
    }

    public void cargar() {
        try (FileInputStream fis = new FileInputStream(archivo)) {
            propiedades.load(fis);
        } catch (IOException e) {
            System.out.println("No se pudo cargar la configuración: " + e.getMessage());
        }
    }

    public void guardar() {
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            propiedades.store(fos, "Configuraciones del sistema");
        } catch (IOException e) {
            System.out.println("No se pudo guardar la configuración: " + e.getMessage());
        }
    }

    public String get(String clave, String valorPorDefecto) {
        return propiedades.getProperty(clave, valorPorDefecto);
    }

    public void set(String clave, String valor) {
        propiedades.setProperty(clave, valor);
        guardar();
    }
}
