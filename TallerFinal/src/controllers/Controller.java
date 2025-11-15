/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import views.VentanaPrincipal;

/**
 * @author Yulian Alexis Tobar Rios
 *  @author Hellen Valeria Melo Cubides
 */
public class Controller implements ActionListener{
    private VentanaPrincipal vista;

    public Controller(VentanaPrincipal vista) {
        this.vista = vista;
        inicializarEventos();
    }

    /**
     * Asigna los ActionCommands a cada botón y registra el listener.
     */
    private void inicializarEventos() {
        vista.btnAgregar.setActionCommand("AGREGAR");
        vista.btnLimpiar.setActionCommand("LIMPIAR");
        vista.btnEditar.setActionCommand("EDITAR");
        vista.btnEliminar.setActionCommand("ELIMINAR");
        vista.btnGuardar.setActionCommand("GUARDAR");
        vista.btnCambiarUsuario.setActionCommand("CAMBIAR_USUARIO");
        vista.btnCambiarTema.setActionCommand("CAMBIAR_TEMA");

        vista.btnAgregar.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnGuardar.addActionListener(this);
        vista.btnCambiarUsuario.addActionListener(this);
        vista.btnCambiarTema.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "AGREGAR":
                agregarTarea();
                break;

            case "LIMPIAR":
                limpiarCampos();
                break;

            case "EDITAR":
                editarTarea();
                break;

            case "ELIMINAR":
                eliminarTarea();
                break;

            case "GUARDAR":
                guardarCambios();
                break;

            case "CAMBIAR_USUARIO":
                cambiarUsuario();
                break;

            case "CAMBIAR_TEMA":
                cambiarTema();
                break;

            default:
                JOptionPane.showMessageDialog(vista, "Acción no reconocida: " + comando);
        }
    }

    // ============================================================
    // Métodos individuales (vacíos por ahora, con comentarios guía)
    // ============================================================

    private void agregarTarea() {
        // Validaciones básicas de los campos
        String titulo = vista.txtTitulo.getText().trim();
        String fecha = vista.txtFechaLimite.getText().trim();
        String descripcion = vista.txtDescripcion.getText().trim();

        if (titulo.isEmpty() || titulo.equals("Título")) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingresa un título para la tarea.");
            return;
        }
        if (fecha.isEmpty() || fecha.equals("Fecha límite (AAAA-MM-DD)")) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingresa una fecha válida.");
            return;
        }

        String estado = "Pendiente";
        if (vista.rProgreso.isSelected()) estado = "En progreso";
        else if (vista.rCompletada.isSelected()) estado = "Completada";

        // Aquí luego se creará una nueva Tarea y se guardará usando GestorTareas
        JOptionPane.showMessageDialog(vista,
                "Tarea agregada:\n" +
                        "Título: " + titulo + "\n" +
                        "Fecha: " + fecha + "\n" +
                        "Estado: " + estado + "\n" +
                        "Descripción: " + descripcion);
    }

    private void limpiarCampos() {
        vista.txtTitulo.setText("Título");
        vista.txtTitulo.setForeground(java.awt.Color.GRAY);
        vista.txtFechaLimite.setText("Fecha límite (AAAA-MM-DD)");
        vista.txtFechaLimite.setForeground(java.awt.Color.GRAY);
        vista.txtDescripcion.setText("Descripción");
        vista.txtDescripcion.setForeground(java.awt.Color.GRAY);
        vista.rPendiente.setSelected(true);
    }

    private void editarTarea() {
        // Aquí luego se obtendrá la tarea seleccionada en la tabla y se editará
        JOptionPane.showMessageDialog(vista, "Editar tarea (lógica pendiente)");
    }

    private void eliminarTarea() {
        // Aquí luego se eliminará la tarea seleccionada de la tabla y del archivo JSON
        JOptionPane.showMessageDialog(vista, "Eliminar tarea (lógica pendiente)");
    }

    private void guardarCambios() {
        // Aquí luego se guardarán todas las tareas actualizadas usando GestorTareas
        JOptionPane.showMessageDialog(vista, "Guardar cambios (lógica pendiente)");
    }

    private void cambiarUsuario() {
        String nuevoUsuario = JOptionPane.showInputDialog(vista, "Ingrese el nuevo nombre de usuario:");
        if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty()) {
            vista.lblUsuario.setText("Usuario: " + nuevoUsuario);
            JOptionPane.showMessageDialog(vista, "Usuario cambiado a: " + nuevoUsuario);
        }
    }

    private void cambiarTema() {
        // Llama al método que ya tienes en VentanaPrincipal
        vista.btnCambiarTema.doClick();
    }
}
