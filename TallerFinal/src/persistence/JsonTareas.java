/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import models.Tarea;

/**
 *
 * @author Yulian Alexis Tobar Rios
 * @author Hellen Valeria Melo Cubides
 */
public class JsonTareas {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static ArrayList<Tarea> leerArchivo(Path archivo) {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        try {
            Reader lector = Files.newBufferedReader(archivo);
            Tarea[] arreglo = GSON.fromJson(lector, Tarea[].class);
            lector.close();

            ArrayList<Tarea> lista = new ArrayList<>();
            if (arreglo != null) {
                for (Tarea t : arreglo) {
                    lista.add(t);
                }
            }
            return lista;

        } catch (IOException e) {
            System.out.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void escribirArchivo(ArrayList<Tarea> tareas, Path archivo) {
        try {
            if (archivo.getParent() != null) {
                Files.createDirectories(archivo.getParent());
            }

            Writer writer = Files.newBufferedWriter(archivo);
            GSON.toJson(tareas, writer);
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al guardar archivo JSON: " + e.getMessage());
        }
    }

    public static ArrayList<Tarea> leerTareas(Path archivo) {
        return leerArchivo(archivo);
    }

    public static void guardarTareas(ArrayList<Tarea> lista, Path archivo) {
        escribirArchivo(lista, archivo);
    }
}
