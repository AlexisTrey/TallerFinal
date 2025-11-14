/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.nio.file.Path;
import java.util.ArrayList;
import persistence.JsonTareas;

/**
 *
 * @author Yulian Alexis Tobar Rios
 * @author Hellen Valeria Melo Cubides
 */
public class GestorTareas {
    private ArrayList<Tarea> tareas = new ArrayList<>();

    public void agregarTarea(Tarea nueva) {
        tareas.add(nueva);
    }

    public void editarTarea(int index, Tarea nueva) {
        if (index >= 0 && index < tareas.size()) {
            tareas.set(index, nueva);
        }
    }

    public void eliminarTarea(int index) {
        if (index >= 0 && index < tareas.size()) {
            tareas.remove(index);
        }
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void cargar(Path archivo) {
        this.tareas = JsonTareas.leerTareas(archivo);
    }

    public void guardar(Path archivo) {
        JsonTareas.guardarTareas(tareas, archivo);
    }
}
